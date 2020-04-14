package vlb.ide.utils.file;

import javax.swing.ProgressMonitor;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import com.jcraft.jsch.UserInfo;


public class SFTPFile {
	public static void main(String[] arg){
		new SFTPFile("reftab", "unix11" ,"cmis463", 22, "c:\\233144_mod_users.txt", "/global/crmprl01/fs_users/reftab/sView_Bulk_User/233144_mod_users.txt", 511);
	}
	
	public SFTPFile(String user, String pass, String host, int port, String from, String to, int permissions){
		JSch jsch=new JSch();
		Session session;
		try {
			session = jsch.getSession(user, host, port);

			UserInfo ui=new MyUserInfo(pass);
			session.setUserInfo(ui);
			session.connect();

			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp c=(ChannelSftp)channel;
			
			int mode=ChannelSftp.OVERWRITE;
			SftpProgressMonitor monitor=new MyProgressMonitor();
			c.put(from, to, monitor, mode); 
			System.out.println("put " + from + " " + to); 
			
			 
			c.chmod(permissions, to);
			System.out.println("chmod " + Integer.toOctalString(permissions) + " " + to); 
			
			System.out.println("complete");
			c.quit();
			c.exit();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
	}

	public static class MyUserInfo implements UserInfo{

		private String passwd = null;

		public MyUserInfo(String passwd) {
			this.passwd = passwd;
		}

		
		public String getPassphrase() {
			return null;
		}

		
		public String getPassword() {
			return passwd;
		}

		
		public boolean promptPassphrase(String str) {
			return true;
		}

		
		public boolean promptPassword(String str) {
			return true;
		}

		
		public boolean promptYesNo(String str) {
			return true;
		}

		
		public void showMessage(String str) {
			
		}
		
	}
	
	public static class MyProgressMonitor implements SftpProgressMonitor{
	    ProgressMonitor monitor;
	    long count=0;
	    long max=0;
	    public void init(int op, String src, String dest, long max){
	      this.max=max;
	      monitor=new ProgressMonitor(null, 
	                                  ((op==SftpProgressMonitor.PUT)? 
	                                   "put" : "get")+": "+src, 
	                                  "",  0, (int)max);
	      count=0;
	      percent=-1;
	      monitor.setProgress((int)this.count);
	      monitor.setMillisToDecideToPopup(1000);
	    }
	    private long percent=-1;
	    public boolean count(long count){
	      this.count+=count;

	      if(percent>=this.count*100/max){ return true; }
	      percent=this.count*100/max;

	      monitor.setNote("Completed "+this.count+"("+percent+"%) out of "+max+".");     
	      monitor.setProgress((int)this.count);

	      return !(monitor.isCanceled());
	    }
	    public void end(){
	      monitor.close();
	    }
	  }

}
