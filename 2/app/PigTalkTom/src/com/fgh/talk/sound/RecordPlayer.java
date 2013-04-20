package com.fgh.talk.sound;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import com.badlogic.gdx.audio.transform.SoundTouch;
import com.fgh.talk.TalkMainActivity;
import pigframe.util.PigDataTypeChangeUtil;;;
public class RecordPlayer  {

	public final static String APP_FILE_PATH_START = "/data/data/";
	private File recordingFile;
	boolean isRecording = false;
	boolean isPlaying = false;
	private int arbs;
	private AudioRecord ar;
    private int atbs;
    private  AudioTrack at ;
   
    // 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025 
    private int frequency = 8000;
	private SoundTouch soundTouch ;
	
	private  short[] one_mic_sample;
	
	/**
	 * 缓存了多少个采样帧
	 */
	private int  one_mic_sample_len; 
	/**
	 * record the mic test samples 
	 */
	private ArrayList<short[]>  mic_sample_list;
	
	public RecordPlayer() {
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		
			mic_sample_list=new ArrayList<short[]>();
			
			soundTouch = new SoundTouch();
			soundTouch.setSetting(0, 1);
		    soundTouch.setSetting(1, 40);
		    soundTouch.setSetting(2, 1);
		    soundTouch.setSetting(3, 40);
		    soundTouch.setSetting(4, 15);
		    soundTouch.setSetting(5, 8);
			soundTouch.setSampleRate(frequency);
//			soundTouch.setPitchSemiTones(5);
			soundTouch.setPitch(1.5f);
			soundTouch.setChannels(1);
		
			//create the pcm file
			String packageName = TalkMainActivity.class.getPackage().getName();
			String soundpath = APP_FILE_PATH_START + packageName
											+ "/" + "temp" + "/";
			File path = new File(soundpath);
			path.mkdirs();
			try {
				recordingFile = File.createTempFile("recording", ".pcm", path);
			} catch (IOException e) {
				throw new RuntimeException("Couldn't create file on SD card", e);
			}
		
			
			////init ar  and at
		    arbs = AudioRecord.getMinBufferSize(
	        		frequency,
	                AudioFormat.CHANNEL_CONFIGURATION_MONO,
	                AudioFormat.ENCODING_PCM_16BIT);
	        ar = new AudioRecord(MediaRecorder.AudioSource.MIC,
	        		frequency,
	                AudioFormat.CHANNEL_CONFIGURATION_MONO,
	                AudioFormat.ENCODING_PCM_16BIT, arbs);
	        
	        atbs = AudioTrack.getMinBufferSize(frequency,
	        		 AudioFormat.CHANNEL_CONFIGURATION_MONO, 
	        		 AudioFormat.ENCODING_PCM_16BIT);
	        
	        at = new AudioTrack(
					 AudioManager.STREAM_MUSIC, 
					 frequency,
					 AudioFormat.CHANNEL_CONFIGURATION_MONO,
					 AudioFormat.ENCODING_PCM_16BIT, 
					 atbs,
					 AudioTrack.MODE_STREAM);
	}



	public void stopPlaying() {
		isPlaying = false;
	}


	public void stopRecording() {
		isRecording = false;
	}

	
	public void deleteTempFile(){
		if(recordingFile.delete()){
			Log.i("TempFile", "delete success");
		}else{
			Log.i("TempFile", "delete failed");
		};
	}
	public boolean  startPlayPcm(){
		isPlaying = true;
		short[] audiodata = new short[atbs ];
		try {
			DataInputStream dis = new DataInputStream(
					new BufferedInputStream(new FileInputStream(
							recordingFile)));
			at.play();

			while (isPlaying && dis.available() > 0) {
				int i = 0;
				while (dis.available() > 0 && i < audiodata.length) {
//					audiodata[i] = dis.readByte();
					audiodata[i] = dis.readShort();
					i++;
				}
				at.write(audiodata, 0, audiodata.length);
			}
			at.stop();
			dis.close();
			
		} catch (Throwable t) {
			Log.e("AudioTrack", "Playback Failed");
		}

		return true;
	}
	
	
	
	/**
	 * @param dos 
	 * @throws IOException
	 */
	private void writeBuffeSamples(DataOutputStream dos) throws IOException{
		for (short[] test_buff_item : mic_sample_list) {
			 soundTouch.putSamples(test_buff_item, 0, one_mic_sample_len);
	          
	            int numSamples1=0;
	            do{
	            	short[] samples = new short[one_mic_sample_len];
	            	numSamples1=soundTouch.receiveSamples(samples, 0, one_mic_sample_len);
	            	for (int i = 0; i < numSamples1; i++) {
						dos.writeShort(samples[i]);
					}
	            } while(numSamples1!=0); 
		}
	}
	
	public boolean  startRecord() {
		isRecording = true;
		try {
			
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(
							recordingFile)));
			//检测分贝的那些声音加上
//			dos.write(one_mic_sample,0,one_mic_sample_len);
			writeBuffeSamples(dos);
			
			
			short[] buffer = new short[arbs];
			int mark=0;
			boolean pre_mark=false;
			while (isRecording) {
				//检测后，又开始读取一个sample（相当于一帧）
				int bufferReadResult = ar.read(buffer, 0,
						arbs);
				//读取声音大小
				int dB=getVolume(buffer, bufferReadResult);
	            Log.d("writeRecord2File", dB+"");
	            if(!pre_mark){
	        	   mark=0;
	            }
	            //声音过小累计次数，就停止录音
	            if(dB<SoundConstant.MinDecibel){
	            	pre_mark=true;
	            	mark++;
	 	            Log.i("stop mark",  mark+"");
	 	            if(mark>=SoundConstant.STOP_COUNT){
	 	            	isRecording=false;
	 	            }
	            }else{
	            	pre_mark=false;
	            }
	            
	            //用ST处理  put进去
	            //void com.badlogic.gdx.audio.transform.SoundTouch.putSamples
	            //(short[] samples, int offset, int numSamples)
	            soundTouch.putSamples(buffer, 0, bufferReadResult);
		          
	            int numSamples=0;
	            do{
	            	short[] newsamples = new short[bufferReadResult];
	            	numSamples=soundTouch.receiveSamples(newsamples, 0, bufferReadResult);
	            	for (int i = 0; i < numSamples; i++) {
						dos.writeShort(newsamples[i]);
					}
	            } while(numSamples!=0); 
	           
			}
//			audioRecord.stop();
			dos.close();
		} catch (Throwable t) {
			Log.e("AudioRecord", "Recording Failed");
			Log.e("AudioRecord", t.getMessage());
			return false;
		}
		return true;

	}
	
	public void startLiternMic(){
		ar.startRecording();
	}
	
	public void release(){
			ar.stop();
	        at.stop();
	        ar.release();
	        at.release();
	}
	
	/**
	 * @param buffer
	 * @param bufferReadResult
	 * @return 获得mic的声音大小
	 */
	private int getVolume(short[] buffer,int bufferReadResult){
		int v = 0;
		// 将 buffer 内容取出，进行平方和运算
		for (int i = 0; i < buffer.length; i++) {
			// 这里没有做运算的优化，为了更加清晰的展示代码
			byte[] byte2=PigDataTypeChangeUtil. shortToByteArray(buffer[i]);
			v += byte2[0] * byte2[0];
			v += byte2[1] * byte2[1];
			
		}
		// 平方和除以数据总长度，得到音量大小。可以获取白噪声值，然后对实际采样进行标准化。
		// 如果想利用这个数值进行操作，建议用 sendMessage 将其抛出，在 Handler 里进行处理。
//        Log.d("spl", String.valueOf(v / (float) r));
//        double dB = 10*Math.log10(v/(double)bufferReadResult);
		int dB = v /(bufferReadResult*2);
		return dB;
	}
	
	public int getVolume(){
			one_mic_sample=null;
			one_mic_sample = new short[arbs];
			one_mic_sample_len = ar.read(one_mic_sample, 0,
					arbs);
			int dB=getVolume(one_mic_sample,one_mic_sample_len);;
			if(dB>=SoundConstant.MinDecibel){
				mic_sample_list.add(one_mic_sample);
			}else{
				mic_sample_list.clear();
			}
		return dB;
	}
	
	public int getPlayStates(){
		return at.getPlayState();
	}
	
	
	 public void rePlay(){
	        if(!isPlaying&&!isRecording
	            		&&at.getPlayState()!=AudioTrack.PLAYSTATE_PLAYING){
	        	try {
	        		//开始录音
	        		if(startRecord()){
	            		//停止录音
	            		stopRecording();
	            		//休息休息
//	            		Thread.sleep(100);
	            		//开始播放录音
	            		if(startPlayPcm()){
	            			stopPlaying();
	            			//删除录音文件
	            			deleteTempFile();
	            		};
	            		
	            	};
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	    }
}
