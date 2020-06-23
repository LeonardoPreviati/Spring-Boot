package br.com.empreendedorismo.util;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.web.bind.annotation.RestController;
import br.com.empreendedorismo.entity.DPUser;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmailUtil {
	
	public void sendEmail (DPUser user, String confirmationToken) throws EmailException {
		HtmlEmail htmlMail = new HtmlEmail();
		htmlMail.setHostName(ConstantsUtil.EMAIL_SERVER_HOSTNAME);
		htmlMail.setSmtpPort(ConstantsUtil.SMPT_PORT);
		htmlMail.setAuthenticator(new DefaultAuthenticator(ConstantsUtil.EMAIL_FROM, ConstantsUtil.PASSWORD_DISCOVER_PROFILE));
		htmlMail.setSSLOnConnect(true);
		htmlMail.setFrom(ConstantsUtil.EMAIL_FROM);
	    if (!confirmationToken.equals("")) {
	    	htmlMail.setSubject(ConstantsUtil.SUBJECT_ACTIVE);
	    	htmlMail.setTextMsg(ConstantsUtil.HELLO_MSG + user.getName() + ConstantsUtil.MSG_ACTIVE + ConstantsUtil.LINK_ACTIVE + confirmationToken);
	    }else {
	    	htmlMail.setSubject(ConstantsUtil.SUBJECT);
	    	htmlMail.setTextMsg(ConstantsUtil.HELLO_MSG + user.getName() + ConstantsUtil.BODY_MSG);
        }htmlMail.addTo(user.getEmail());
         htmlMail.send();
	     if (confirmationToken.equals("")) {
	    	 log.info(ConstantsUtil.INFO_USER +  user.getName() + ConstantsUtil.INFO_LOGIN);
	     }
	}
}
