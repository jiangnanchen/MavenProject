package utils;

public class Constants {
	//IE�����ļ�
	public static final String IE_DRIVER=System.getProperty("user.dir")+"\\driver\\IEDriverServer.exe";
	//Chrome�����ļ�
	public static final String CHROME_DRIVER=System.getProperty("user.dir")+"\\driver\\chromedriver2.48.2.exe";
	//Ĭ�ϵȴ���ʱʱ��
	public static final long WAIT_TIME= 30;
	//��ʽĬ�ϵȴ���ʱʱ��
	public static final int EXPLICIT_WAIT= 30;
	
	//Ĭ�������ļ���·��
	public static final String DOWNLOAD_PATH=System.getProperty("user.dir")+"\\download";
	//��ͼ�ļ�·��
	public static final String SCREENSHOT = System.getProperty("user.dir")+"\\screenshots\\";
	//��������·��
	public static final String DATA_PATH = System.getProperty("user.dir")+"\\data";
	//ECSHOP�߼���������ַ
	public static final String ECSHOP_ADVANCED_SEARCH_URL = "http://127.0.0.1/upload/search.php?encode=YToyOntzOjM6ImFjdCI7czoxNToiYWR2YW5jZWRfc2VhcmNoIjtzOjE4OiJzZWFyY2hfZW5jb2RlX3RpbWUiO2k6MTU0MTQzMDkzMzt9";
}
