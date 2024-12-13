package acc.br.student_registration.client;

import acc.br.student_registration.dto.AddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url= "https://viacep.com.br/ws/" , name = "viacep")
public interface ViaCepClient {
    @GetMapping("/{cep}/json/")
    AddressDTO getAddressByCep(@PathVariable("cep") String cep);
}
