package br.com.empreendedorismo.util;

public class ConstantsUtil {
	
	//Configuration parameters for send email
	public static final String USER_ACCOUNT_ALREADY_REGISTERED = "A conta já foi registrada";
	public static final String USER_ACCOUNT_NOT_REGISTERED = "Não foi registrar a sua conta :(. Contate o administrador do sistema, para mais informações";
	public static final String USER_ACCOUNT_REGISTERED = "Conta registrada com sucesso";
	public static final String INFO_USER = "User ";
	public static final String INFO_LOGIN = ", logged into the system.";
	public static final String HELLO_MSG = "Olá ";
	public static final String LINK_ACTIVE = "http://localhost:8080/dpUser/confirm-account?token=";
	public static final String MSG_ACTIVE = "\n\nClique no link para ativar a sua conta: ";
	public static final String BODY_MSG = ",\n\nEsta é uma notificação para confirmar seu login efetuado com sucesso "
 		   + "na Discover Profile.\n\nAtenciosamente,\nEquipe Discover Profile";
	public static final String SUBJECT_ACTIVE = "Discover Profile: Ativação da conta";
	public static final String SUBJECT = "Discover Profile: Login Realizado";
	public static final String EMAIL_FROM = "discover.profile@gmail.com";
	public static final String PASSWORD_DISCOVER_PROFILE = "DiscoProfileVoador#300";
	public static final String EMAIL_SERVER_HOSTNAME = "smtp.googlemail.com";
	public static final int SMPT_PORT = 465;
	//Configuration parameters of cors mappings
	public static final String ALLOWED_ORIGINS_HOSTNAME = "http://localhost:3000";
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	public static final String OPTIONS = "OPTIONS";
	public static final String HEAD = "HEAD";
	public static final String TRACE = "TRACE";
	public static final String CONNECT = "CONNECT"; 
	//Configuration parameters the authorize Requests
	public static final String DP_USER = "/dpUser"; 
	public static final String AUTH = "/auth"; 
	public static final String ACTUATOR = "/actuator"; 
	public static final String QUIZ_RESULTS = "/quizResults/**"; 
	//Name profiles
	public static final String USER = "USER"; 
	public static final String ADMIN = "ADMIN"; 
	public static final String DISCOVER_PROFILE = "DISCOVER_PROFILE";
	//Choice of profile by password 
	public static final String PASSWORD_ADMIN_ENCODE = "$2b$10$rBlfZ0HspWX15PNCdepD8O8ah8GhdncVLmT0t4ecpio1EQs.6JTpm";
	public static final String PASSWORD_DISCOVER_PROFILE_ENCODE = "$2b$10$Nwn3OinM62ipGy5N0x1HGOS3ow8lmqtURFXKVnn7ff3uW7Ym48EU2";
}
