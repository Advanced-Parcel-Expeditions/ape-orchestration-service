package si.ape.orchestration.lib.requests.authentication;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbProperty;

public class RegisterCustomerRequest {

    // User data.

    @Schema(description = "Username of the user.", example = "janez", required = true)
    @JsonbProperty("username")
    private String username;

    @Schema(description = "Password of the user.", example = "janez", required = true)
    @JsonbProperty("password")
    private String password;

    // Customer data.

    @Schema(description = "First name of the customer.", example = "Janez", required = true)
    @JsonbProperty("firstName")
    private String firstName;

    @Schema(description = "Last name of the customer.", example = "Novak", required = true)
    @JsonbProperty("lastName")
    private String lastName;

    @Schema(description = "Company of the user.", example = "APE d.o.o.", required = false)
    @JsonbProperty("companyName")
    private String companyName;

    @Schema(description = "Telephone number of the user.", example = "031123456", required = false)
    @JsonbProperty("telephoneNumber")
    private String telephoneNumber;

    // Address data.

    @Schema(description = "Street name of the user.", example = "Cesta v Mestni log", required = true)
    @JsonbProperty("streetName")
    private String streetName;

    @Schema(description = "Street number of the user.", example = "1", required = true)
    @JsonbProperty("streetNumber")
    private Integer streetNumber;

    @Schema(description = "Postal code of the user.", example = "1000", required = true)
    @JsonbProperty("postalCode")
    private String cityCode;

    @Schema(description = "City name of the user.", example = "Ljubljana", required = true)
    @JsonbProperty("cityName")
    private String cityName;

    @Schema(description = "ISO 3 country code of the user.", example = "SVN", required = true)
    private String countryCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

}
