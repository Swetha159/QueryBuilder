package querybuilder;

public class Nominee {       
    private Long customerId;
    private Long accountNo;
    private String nomineeName;

    public Nominee() {}

    public Nominee(Long customerId, Long accountNo, String nomineeName) {
        this.customerId = customerId;
        this.accountNo = accountNo;
        this.nomineeName = nomineeName;
    }

    public Nominee(Long nomineeId, Long customerId, Long accountNo, String nomineeName) {
      
        this.customerId = customerId;
        this.accountNo = accountNo;
        this.nomineeName = nomineeName;
    }

  
   

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getAccountNo() { return accountNo; }
    public void setAccountNo(Long accountNo) { this.accountNo = accountNo; }

    public String getNomineeName() { return nomineeName; }
    public void setNomineeName(String nomineeName) { this.nomineeName = nomineeName; }
}

