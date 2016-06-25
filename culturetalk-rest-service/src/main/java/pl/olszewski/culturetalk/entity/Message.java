package pl.olszewski.culturetalk.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "message", schema = "culturetalk")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_message")
	private Integer idMessage;

	@ManyToOne
	@JoinColumn(name = "user_from")
	private User userFrom;

	@ManyToOne
	@JoinColumn(name = "user_to")
	private User userTo;

	@Column(name = "date_sent")
	private Date dateSent;

	private String message;

	public Message() {
	};

	public Message(User idUserFrom, User idUserTo, Date dateSent, String message) {
		super();
		this.userFrom = idUserFrom;
		this.userTo = idUserTo;
		this.dateSent = dateSent;
		this.message = message;
	}

	public Integer getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(Integer idMessage) {
		this.idMessage = idMessage;
	}

	public User getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(User idUserFrom) {
		this.userFrom = idUserFrom;
	}

	public User getUserTo() {
		return userTo;
	}

	public void setUserTo(User idUserTo) {
		this.userTo = idUserTo;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
