package otus.spring.main.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class ExamineMessageSource {

	private Locale language;
	private MessageSource messageSource;
	
	public ExamineMessageSource(@Value("${user.locale}")Locale language) 
	{
		this.language = language;
		this.messageSource = defineMessageSource();
	}
	
	private MessageSource defineMessageSource() 
	{ 
		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
		ms.setBasename("i18n/bundle");
		return ms;
	}
	public MessageSource getMessageSource()
	{
		return this.messageSource;
	}
	public String getMessage(String bundle)
	{
		return this.messageSource.getMessage(bundle, null, language);
	}
	public String getMessage(String bundle, String params)
	{
		return this.messageSource.getMessage(bundle, new String[] {params}, language);
	}
}
