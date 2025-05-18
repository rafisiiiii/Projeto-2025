package com.projeto2025.api_projeto.services;

import com.projeto2025.api_projeto.entities.Cliente;
import com.projeto2025.api_projeto.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final ClienteRepository clienteRepository;
    private final TemplateEngine templateEngine;

    public byte[] gerarRelatorioClientesPdf() throws Exception {
        List<Cliente> clientes = clienteRepository.findAll();

        Context context = new Context();
        context.setVariable("clientes", clientes);

        String html = templateEngine.process("relatorio-clientes.html", context);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(out);
            builder.run();
            return out.toByteArray();
        }
    }
}
