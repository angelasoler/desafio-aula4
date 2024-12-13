package acc.br.student_registration.service;

import org.springframework.stereotype.Service;

import acc.br.student_registration.client.ViaCepClient;
import acc.br.student_registration.dto.AddressDTO;
import acc.br.student_registration.exception.AddressNotFoundException;


@Service
public class AddressService {
    private final ViaCepClient viaCepClient;

    public AddressService(ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    public AddressDTO getAddress(String cep) {
        AddressDTO AddressDTO = viaCepClient.getAddressByCep(cep);
        if (AddressDTO == null || AddressDTO.getCep() == null) {
            throw new AddressNotFoundException("Address not found for CEP: " + cep);
        }
        return AddressDTO;
    }
}
