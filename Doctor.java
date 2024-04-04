
public class Doctor extends User{
    private int doctorId;
    private String medicalLicenseNumber;
    private String specialization;

    public Doctor(String firstName, String lastName, String email, String password, boolean isDoctor, String medicalLicenseNumber, String specialization) {
        super(firstName, lastName, email, password, isDoctor);
        this.doctorId = DoctorPortalDao.getDoctorIdByEmail(email);
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.specialization = specialization;
    }

    // Getters and setters for the new properties
    public int getDoctorId(){
        return this.doctorId;
    }
    public void setDoctorId(int doctorId){
        this.doctorId = doctorId;
    }
    public String getMedicalLicenceNumber(){
        return this.medicalLicenseNumber;
    }
    public void setMedicalLicenceNumber(String medicalLicenceNumber){
        this.medicalLicenseNumber = medicalLicenceNumber;
    }
    public String getSpecialization(){
        return this.specialization;
    }
    public void setSpecialization(String specialization){
        this.specialization = specialization;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + Doctor.super.getFirstName() + " " + Doctor.super.getLastName());
        sb.append("\nEmail: " + email);
        sb.append("\nMedical Licence Number: " + medicalLicenseNumber);
        sb.append("\nSpecialization: " + specialization);
        String result = sb.toString();
        return result;
    }
}

