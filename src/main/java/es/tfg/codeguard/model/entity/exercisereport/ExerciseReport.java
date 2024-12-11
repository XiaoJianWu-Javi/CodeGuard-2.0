package es.tfg.codeguard.model.entity.exercisereport;

import es.tfg.codeguard.util.EmptyReportMessageException;
import jakarta.persistence.*;

@Entity
@Table(name = "exercise_reports")
public class ExerciseReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 750)
    private String description;
    
    @Column(nullable = false, length = 750)
    private String adminResponse;

    @Column(name="user_name", nullable = false)
    private String userName;

    @Column(name="admin_name")
    private String adminName;
    
    @Column(nullable = false)
    private boolean resolved;

    //Constructors
	public ExerciseReport() {
		super();
		setAdminResponse("");
		setResolved(false);
	}

	public ExerciseReport(String description, String userName){
		super();
		setAdminResponse("");
		setDescription(description);
		setUserName(userName);
	}

	public ExerciseReport(String description, String adminResponse, String userName, String adminName,
			boolean resolved){
		super();
		setDescription(description);
		setUserName(userName);
		setAdminResponse(adminResponse);
		setAdminName(adminName);
		setResolved(resolved);
	}


	//Setters
	public void setDescription(String description){
		if(description==null||description.isBlank()) throw new EmptyReportMessageException("Empty Report Message");
		this.description = description;
	}

	public void setAdminResponse(String adminResponse) {
		this.adminResponse = adminResponse;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public void setUserName(String userName) {
		if(userName==null||userName.isBlank()) throw new EmptyReportMessageException("Empty Report Message");
		this.userName = userName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
	
	//Getter
	
	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
	public String getAdminResponse() {
		return adminResponse;
	}

	public boolean isResolved() {
		return resolved;
	}

   	public String getUserName() {
		return userName;
	}
 

	public String getAdminName() {
		return adminName;
	}


}
