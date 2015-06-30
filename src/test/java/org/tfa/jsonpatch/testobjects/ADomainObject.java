package org.tfa.jsonpatch.testobjects;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tfa.jsonpatch.annotations.PatchIgnore;
import org.tfa.jsonpatch.annotations.PatchIncludeNull;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ADomainObject {
	private List<String> alist;
	
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public class EnterpriseRole{
		@PatchIncludeNull
		private String program;
		private String affiliation;
		@PatchIncludeNull
		private String exit;
		private String status;
		
		public String getProgram() {
			return program;
		}
		public void setProgram(String program) {
			this.program = program;
		}
		public String getAffiliation() {
			return affiliation;
		}
		public void setAffiliation(String affiliation) {
			this.affiliation = affiliation;
		}
		public String getExit() {
			return exit;
		}
		public void setExit(String exit) {
			this.exit = exit;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		@Override
		public String toString() {
			return "EnterpriseRole [program=" + program + ", affiliation="
					+ affiliation + ", exit=" + exit + ", status=" + status
					+ "]";
		}
	}
	
	@PatchIgnore
	private String ldapGuid;
	private String email;
	private String tfaUserName;
	private String givenName;
	private String surname;
	private String password;
	private EnterpriseRole enterpriserole;
	private String tfaPersonID;
	private String essn;
	private String tfaCurrentCMRegion;
	private String tfaCorpsYear;
	private String tfaInstKey;
	private String tfaInstYear;
	private String ssn4;
	private String workerID;
	
	@JsonIgnore
    private String dispositionStage;
    @JsonIgnore
    private String dispositionStep;
    @JsonIgnore
    private String dispositionExitCode;
    @JsonIgnore
    private String personType;
    /**
     * Redwood Region Code
     */
    @JsonIgnore
    private String currentRegion;
    
    
    public List<String> getAlist() {
		return alist;
	}
	public void setAlist(List<String> alist) {
		this.alist = alist;
	}
	public String getLdapGuid() {
		return ldapGuid;
	}
	public void setLdapGuid(String ldapGuid) {
		this.ldapGuid = ldapGuid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTfaUserName() {
		return tfaUserName;
	}
	public void setTfaUserName(String tfaUserName) {
		this.tfaUserName = tfaUserName;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public EnterpriseRole getEnterpriserole() {
		return enterpriserole;
	}
	public void setEnterpriserole(EnterpriseRole enterpriserole) {
		this.enterpriserole = enterpriserole;
	}
	public String getTfaPersonID() {
		return tfaPersonID;
	}
	public void setTfaPersonID(String tfaPersonID) {
		this.tfaPersonID = tfaPersonID;
	}
	public String getEssn() {
		return essn;
	}
	public void setEssn(String essn) {
		this.essn = essn;
	}
	public String getTfaCurrentCMRegion() {
		return tfaCurrentCMRegion;
	}
	public void setTfaCurrentCMRegion(String tfaCurrentCMRegion) {
		this.tfaCurrentCMRegion = tfaCurrentCMRegion;
	}
	public String getTfaCorpsYear() {
		return tfaCorpsYear;
	}
	public void setTfaCorpsYear(String tfaCorpsYear) {
		this.tfaCorpsYear = tfaCorpsYear;
	}
	public String getTfaInstKey() {
		return tfaInstKey;
	}
	public void setTfaInstKey(String tfaInstKey) {
		this.tfaInstKey = tfaInstKey;
	}
	public String getTfaInstYear() {
		return tfaInstYear;
	}
	public void setTfaInstYear(String tfaInstYear) {
		this.tfaInstYear = tfaInstYear;
	}
	public String getSsn4() {
		return ssn4;
	}
	public void setSsn4(String ssn4) {
		this.ssn4 = ssn4;
	}
	public String getWorkerID() {
		return workerID;
	}
	public void setWorkerID(String workerID) {
		this.workerID = workerID;
	}
	
	
	public String getDispositionStage() {
		return dispositionStage;
	}
	public void setDispositionStage(String dispositionStage) {
		this.dispositionStage = dispositionStage;
	}
	public String getDispositionStep() {
		return dispositionStep;
	}
	public void setDispositionStep(String dispositionStep) {
		this.dispositionStep = dispositionStep;
	}
	public String getDispositionExitCode() {
		return dispositionExitCode;
	}
	public void setDispositionExitCode(String dispositionExitCode) {
		this.dispositionExitCode = dispositionExitCode;
	}
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public String getCurrentRegion() {
		return currentRegion;
	}
	public void setCurrentRegion(String currentRegion) {
		this.currentRegion = currentRegion;
	}
	@Override
	public String toString() {
		return "IdentityDTO [ldapGuid=" + ldapGuid + ", email=" + email
				+ ", tfaUserName=" + tfaUserName + ", givenName=" + givenName
				+ ", surname=" + surname + ", password=" + password
				+ ", enterpriserole=" + enterpriserole + ", tfaPersonID="
				+ tfaPersonID + ", essn=" + essn + ", tfaCurrentCMRegion="
				+ tfaCurrentCMRegion + ", tfaCorpsYear=" + tfaCorpsYear
				+ ", tfaInstKey=" + tfaInstKey + ", tfaInstYear=" + tfaInstYear
				+ ", ssn4=" + ssn4 + ", workerID=" + workerID
				+ ", dispositionStage=" + dispositionStage
				+ ", dispositionStep=" + dispositionStep
				+ ", dispositionExitCode=" + dispositionExitCode
				+ ", personType=" + personType + ", currentRegion="
				+ currentRegion + "]";
	}


}
