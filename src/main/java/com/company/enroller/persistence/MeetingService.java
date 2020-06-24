package com.company.enroller.persistence;

import java.util.Collection;

import com.company.enroller.model.Participant;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Meeting findById(long id) {
		return (Meeting) connector.getSession().get(Meeting.class, id);
	}

	public void add(Meeting meeting) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();
	}

	public Collection<Participant> findParticipantsById(long id) {
		String hql = "select p.login, p.password from Meeting m join m.participants p where m.id = " + id + ")";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public void addParticipantToMeeting(Participant participant, long id) {
		Meeting meeting = findById(id);
		meeting.addParticipant(participant);
		add(meeting);
	}

	public void delete(long id) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		Meeting meeting = findById(id);
		connector.getSession().delete(meeting);
		transaction.commit();
	}
}
