package pigframe.cache;

import android.os.AsyncTask;
import android.os.Handler.Callback;

public class DeleteFilesTask extends AsyncTask<String, Integer, Boolean> {

	private Runnable postCallback;

	public DeleteFilesTask(Runnable cb) {
		// TODO Auto-generated constructor stub
		postCallback = cb;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		// TODO Auto-generated method stub
		if(null != params)
		{
			boolean isOk = true;
			for(int i = 0; i < params.length; i++)
			{
				isOk = isOk && DataFile.deleteDirPath(params[i]);
			}
			return isOk;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		postCallback.run();
	}
	
	

}
