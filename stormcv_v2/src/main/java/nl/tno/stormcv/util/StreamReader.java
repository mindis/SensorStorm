package nl.tno.stormcv.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import nl.tno.stormcv.particle.Frame;
import nl.tno.timeseries.interfaces.Fetcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.utils.Utils;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;

/**
 * This is a utility class used by @link {@link Fetcher} implementations. It reads a video stream or file, decodes 
 * frames and puts those in a queue for further processing. The StreamReader will automatically throttle itself based on the size of the
 * queue it writes the frames to. This is done to avoid memory overload if production of frames is higher than the consumption.
 *  
 * @author Corne Versloot
 *
 */
public class StreamReader extends MediaListenerAdapter implements Runnable {
	
	private Logger logger = LoggerFactory.getLogger(StreamReader.class);
	private IMediaReader mediaReader;
    private int mVideoStreamIndex = -1;
	private String streamId;
	private int frameSkip;
	private int groupSize;
	private long frameNr; // number of the frame read so far
	private boolean running = false; // indicator if the reader is still active
	private LinkedBlockingQueue<Frame> frameQueue; // queue used to store frames
	private long lastRead = -1; // used to determine if the EOF was reached if Xuggler does not detect it
	private int sleepTime;
	private boolean useSingleID = false;

	private String streamLocation;
	private LinkedBlockingQueue<String> videoList = null;
   
	  public StreamReader( LinkedBlockingQueue<String> videoList, int frameSkip, int groupSize, int sleepTime, boolean uniqueIdPerFile, LinkedBlockingQueue<Frame> frameQueue){
		  this.videoList = videoList;
		  this.frameSkip = Math.max(1, frameSkip);
		  this.groupSize = Math.max(1, groupSize);
		  this.sleepTime = sleepTime;
		  this.frameQueue = frameQueue;
		  this.useSingleID = uniqueIdPerFile;
		  this.streamId = ""+this.hashCode(); // give a default streamId
		  lastRead = System.currentTimeMillis()+10000;
	  }
	
	  public StreamReader( String streamId, String streamLocation, int frameSkip, int groupSize, int sleepTime, LinkedBlockingQueue<Frame> frameQueue){
		  this.streamLocation = streamLocation;
		  this.frameSkip = Math.max(1, frameSkip);
		  this.groupSize = Math.max(1, groupSize);
		  this.sleepTime = sleepTime;
		  this.frameQueue = frameQueue;
		  this.streamId = streamId;
		  lastRead = System.currentTimeMillis()+10000;
	  }
	  
    /**
     * Start reading the provided URL
     */
	public void run(){
		running = true;
		while(running){
			try{
				// if a weblocation was provided read it
				if(streamLocation != null){
					logger.info("Start reading stream: "+streamLocation);
					mediaReader = ToolFactory.makeReader(streamLocation);
				}else if(videoList != null){
					// read next video from the list or block until one is available
					String nextVideo = videoList.take();
					
					if(!useSingleID){
						streamId = ""+nextVideo.hashCode();
						if(nextVideo.contains("/")) streamId = nextVideo.substring(nextVideo.lastIndexOf('/')+1)+"_"+streamId;
					}
					
					mediaReader = ToolFactory.makeReader(nextVideo);
				}else{
					logger.error("No video list or url provided, nothing to read");
					break;
				}
				lastRead = System.currentTimeMillis() + 10000;
	
				mVideoStreamIndex = -1;
				if(!useSingleID) frameNr = 0;
		        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		        mediaReader.addListener(this);
		        while (mediaReader.readPacket() == null && running ) ;
		 
		        // reset internal state
		        mediaReader.close();
		        // delete the file
		        if(videoList != null) new File(mediaReader.getUrl()).delete();
			}catch(Exception e){
				logger.warn("Stream closed unexpectatly: "+e.getMessage(), e);
				// sleep a minute and try to read the stream again
				Utils.sleep(1 * 60 * 1000);
			}
		}
		
        mVideoStreamIndex = -1;
        running = false;
	}
	
	/**
	 * Gets called when FFMPEG decoded a frame
	 */
	public void onVideoPicture(IVideoPictureEvent event) {
		lastRead  = System.currentTimeMillis();
        if (event.getStreamIndex() != mVideoStreamIndex) {
            if (mVideoStreamIndex == -1){
                mVideoStreamIndex = event.getStreamIndex();
            }else return;
        }

        if(frameNr % frameSkip < groupSize) try{
        	BufferedImage frame = event.getImage();
        	byte[] buffer = ImageUtils.imageToBytes(frame, Frame.JPG_IMAGE);
        	frameQueue.put(new Frame(streamId, frameNr, event.getTimeStamp(TimeUnit.MILLISECONDS), Frame.JPG_IMAGE, buffer, new Rectangle(0, 0,frame.getWidth(), frame.getHeight()), null));
        	// enforced throttling
        	if(sleepTime > 0) Utils.sleep(sleepTime);
        	// queue based throttling 
        	if(frameQueue.size() > 20) Utils.sleep(frameQueue.size());
        }catch(Exception e){
        	logger.warn("Unable to process new frame due to: "+e.getMessage(), e);
        }
        
        frameNr++;
    }

	/**
	 * Tells the StreamReader to stop reading frames
	 */
	public void stop(){
		running = false;
	}
	
	/**
	 * Returns whether the StreamReader is still active or not
	 * @return
	 */
	public boolean isRunning(){
		// kill this thread if the last frame read is to long ago (means Xuggler missed the EoF) and clear resources 
		if(lastRead > 0 && System.currentTimeMillis() - lastRead > 3000){
			running = false;
			if(mediaReader != null && mediaReader.getContainer() != null) mediaReader.getContainer().close();
			return this.running;
		}
		return true;
	}
	
}
	
