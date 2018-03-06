/*
 * Created on 18 十月 2017 ( Time 09:23:54 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a basic Primary Key (not composite) 

package cn.mxlog.sscloud.model;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.Date;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "user"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="user", catalog="ss_cloud" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="User.countAll", query="SELECT COUNT(X) FROM User x" )
} )
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", nullable=false)
    private Integer    id           ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="name", length=255)
    private String     name         ;

    @Column(name="username", length=255)
    private String     username     ;

    @Column(name="password", length=255)
    private String     password     ;

    @Column(name="email", length=255)
    private String     email        ;

    @Column(name="phone", length=255)
    private String     phone        ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="expire_date")
    private Date       expireDate   ;

    @Column(name="server_name", length=255)
    private String     serverName   ;

    @Column(name="server", length=255)
    private String     server       ;

    @Column(name="port", length=255)
    private String     port         ;

    @Column(name="port_pwd", length=255)
    private String     portPwd      ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdate")
    private Date       createdate   ;

    @Column(name="createby", length=255)
    private String     createby     ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modifydate")
    private Date       modifydate   ;

    @Column(name="modifyby", length=255)
    private String     modifyby     ;

    @Column(name="status")
    private Integer    status       ;

    @Column(name="is_admin")
    private Integer isAdmin;
	// "userTrafficId" (column "user_traffic_id") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @OneToOne
    @JoinColumn(name="user_traffic_id", referencedColumnName="id")
    private UserTraffic userTraffic ;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public User() {
		super();
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId( Integer id ) {
        this.id = id ;
    }
    public Integer getId() {
        return this.id;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    //--- DATABASE MAPPING : name ( VARCHAR ) 
    public void setName( String name ) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    //--- DATABASE MAPPING : username ( VARCHAR ) 
    public void setUsername( String username ) {
        this.username = username;
    }
    public String getUsername() {
        return this.username;
    }

    //--- DATABASE MAPPING : password ( VARCHAR ) 
    public void setPassword( String password ) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }

    //--- DATABASE MAPPING : email ( VARCHAR ) 
    public void setEmail( String email ) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }

    //--- DATABASE MAPPING : phone ( VARCHAR ) 
    public void setPhone( String phone ) {
        this.phone = phone;
    }
    public String getPhone() {
        return this.phone;
    }

    //--- DATABASE MAPPING : expire_date ( DATETIME ) 
    public void setExpireDate( Date expireDate ) {
        this.expireDate = expireDate;
    }
    public Date getExpireDate() {
        return this.expireDate;
    }

    //--- DATABASE MAPPING : server_name ( VARCHAR ) 
    public void setServerName( String serverName ) {
        this.serverName = serverName;
    }
    public String getServerName() {
        return this.serverName;
    }

    //--- DATABASE MAPPING : server ( VARCHAR ) 
    public void setServer( String server ) {
        this.server = server;
    }
    public String getServer() {
        return this.server;
    }

    //--- DATABASE MAPPING : port ( VARCHAR ) 
    public void setPort( String port ) {
        this.port = port;
    }
    public String getPort() {
        return this.port;
    }

    //--- DATABASE MAPPING : port_pwd ( VARCHAR ) 
    public void setPortPwd( String portPwd ) {
        this.portPwd = portPwd;
    }
    public String getPortPwd() {
        return this.portPwd;
    }

    //--- DATABASE MAPPING : createdate ( DATETIME ) 
    public void setCreatedate( Date createdate ) {
        this.createdate = createdate;
    }
    public Date getCreatedate() {
        return this.createdate;
    }

    //--- DATABASE MAPPING : createby ( VARCHAR ) 
    public void setCreateby( String createby ) {
        this.createby = createby;
    }
    public String getCreateby() {
        return this.createby;
    }

    //--- DATABASE MAPPING : modifydate ( DATETIME ) 
    public void setModifydate( Date modifydate ) {
        this.modifydate = modifydate;
    }
    public Date getModifydate() {
        return this.modifydate;
    }

    //--- DATABASE MAPPING : modifyby ( VARCHAR ) 
    public void setModifyby( String modifyby ) {
        this.modifyby = modifyby;
    }
    public String getModifyby() {
        return this.modifyby;
    }

    //--- DATABASE MAPPING : status ( INT ) 
    public void setStatus( Integer status ) {
        this.status = status;
    }
    public Integer getStatus() {
        return this.status;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setUserTraffic( UserTraffic userTraffic ) {
        this.userTraffic = userTraffic;
    }
    public UserTraffic getUserTraffic() {
        return this.userTraffic;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(name);
        sb.append("|");
        sb.append(username);
        sb.append("|");
        sb.append(password);
        sb.append("|");
        sb.append(email);
        sb.append("|");
        sb.append(phone);
        sb.append("|");
        sb.append(expireDate);
        sb.append("|");
        sb.append(serverName);
        sb.append("|");
        sb.append(server);
        sb.append("|");
        sb.append(port);
        sb.append("|");
        sb.append(portPwd);
        sb.append("|");
        sb.append(createdate);
        sb.append("|");
        sb.append(createby);
        sb.append("|");
        sb.append(modifydate);
        sb.append("|");
        sb.append(modifyby);
        sb.append("|");
        sb.append(status);
        return sb.toString(); 
    } 

}
