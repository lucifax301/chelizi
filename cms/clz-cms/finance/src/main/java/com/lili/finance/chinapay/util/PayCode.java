package com.lili.finance.chinapay.util;
public class PayCode {
	
	 //������Ӧ��(responseCode)��
	  public static final String RESPONSE_CODE_SUCCESS= "0000";//�ύ�ɹ�
	  public static final String RESPONSE_CODE_ERRORA= "0100";//�̻��ύ���ֶγ��ȡ���ʽ����
	  public static final String RESPONSE_CODE_ERRORB= "0101";//�̻���ǩ����
	  public static final String RESPONSE_CODE_ERRORC= "0102";//�����Ѽ������
	  public static final String RESPONSE_CODE_ERRORD= "0103";//�̻��������ʻ����㧁
	  public static final String RESPONSE_CODE_ERRORE= "0104";//�����ܾ�
	  public static final String RESPONSE_CODE_ERRORF= "0105";//�ظ�����
	  
	  //����״̬�루stat����
	  public static final String STAT_CODE_SUCCESS= "s";//���׳ɹ�
	  public static final String STAT_CODE_ACCESS= "2";//�����ѽ���
	  public static final String STAT_CODE_SURE= "3";//������ȷ��
	  public static final String STAT_CODE_HANDLE= "4";//��������
	  public static final String STAT_CODE_SEND= "5";//�ѷ�������
	  public static final String STAT_CODE_BACK= "6";//�������˵�
	  public static final String STAT_CODE_SUBMIT= "7";//�ػ����ύ
	  public static final String STAT_CODE_RESEND= "8";//�ػ��ѷ���
	  public static final String STAT_CODE_REFAIL= "9";//�ػ����˵�

}
