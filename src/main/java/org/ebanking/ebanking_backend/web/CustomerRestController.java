package org.ebanking.ebanking_backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ebanking.ebanking_backend.dtos.CustomerDTO;
import org.ebanking.ebanking_backend.entities.Customer;
import org.ebanking.ebanking_backend.exceptions.CustomerNotFoundException;
import org.ebanking.ebanking_backend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor //injection de dependance par constructeur
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    //@PreAuthorize("hasAuthority('SCOPE_USER')")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<CustomerDTO> customers(){
       return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/search")
    //@PreAuthorize("hasAuthority('SCOPE_USER')")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name="keyword",defaultValue= "")String keyword){
        return bankAccountService.listSearchCustomers(keyword);
    }

    @GetMapping("/customers/{id}")
    //@PreAuthorize("hasAuthority('SCOPE_USER')")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public CustomerDTO getCustomer(@PathVariable(name="id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")//@RequestBody signifie qu'on va recevoir les paramètres du corps de la requete au format JSON
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")//@RequestBody signifie qu'on va recevoir les paramètres du corps de la requete au format JSON
    public CustomerDTO saveCustomer (@RequestBody CustomerDTO customerDTO){
       return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{customerId}")
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomerDTO updateCustomer(@PathVariable Long customerId , @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteCustomer(@PathVariable Long id ) {
        bankAccountService.deleteCustomer(id);
    }

}
