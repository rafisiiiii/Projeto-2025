package com.projeto2025.api_projeto.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void enviarConfirmacaoCadastroHtml(String emailDestino, String razaoSocial, String codigo, String cnpj, String usuario) 
    throws MessagingException {

    Context context = new Context();
    context.setVariable("razaoSocial", razaoSocial);
    context.setVariable("codigo", codigo);
    context.setVariable("cnpj", cnpj);
    context.setVariable("usuario", usuario);
    context.setVariable("dataHora", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

    String htmlContent = templateEngine.process("email-confirmacao.html", context);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    helper.setTo(emailDestino);
    helper.setSubject("Confirmação de Cadastro de Cliente");
    helper.setText(htmlContent, true);

    mailSender.send(message);
}

}
