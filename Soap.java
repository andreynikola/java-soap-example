import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;

public class Soap {

    public void sendRequest() {
        InputStream inputStream = null;
        try {
            SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = factory.createConnection();

            URL url = new URL(config.getUrl());

            String message="<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
                            "   <Body>"+
                            "       <testRequest>"+
                            "            <order>8973240</order>"+
                            "            <amount>10</amount>"+
                            "            <currency>RUB</currency>"+
                            "            <email>info@test.com</email>"+
                            "            <phone>+79277999999</phone>"+
                            "            <card>4111111111111111</card>"+
                            "        </testRequest>"+
                            "    </Body>"+
                            "</Envelope>";

            log.debug("Message: {}", message);

            inputStream = new ByteArrayInputStream(message.getBytes());

            MimeHeaders headers = new MimeHeaders();
            headers.setHeader("Cookie", "session=" + "iuafnioqreufu589g89234r932gr");

            SOAPMessage request = MessageFactory.newInstance().createMessage(headers, inputStream);
            SOAPMessage response = connection.call(request,url);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            response.writeTo(baos);

            log.debug("Body: {}", baos.toString());

            SOAPPart soapPart = response.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPBody body = envelope.getBody();

        } catch (Exception e) {

        }finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException ignore) {}
            }
        }
    }

}
