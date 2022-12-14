package com.example.DAO;

import com.example.Bean.Hostel;

import com.example.Bean.Student;
import com.example.Util.HibernateSessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class HostelDAOImpl implements HostelDAO{

    @Override
    public List<Hostel> getRooms() {
        try (Session session = HibernateSessionUtil.getSession()){
            List<Hostel> roomList = new ArrayList<>();
            for (final Object room : session.createQuery("from Hostel ").list()) {
                roomList.add((Hostel) room);
            }
            return roomList;

        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return null;
        }
    }

    public boolean addRoom(Hostel hostel) {
        System.out.println("Impl:"+hostel);
        try(Session session = HibernateSessionUtil.getSession()){
            Transaction transaction = session.beginTransaction();
            Hostel newHostel = new Hostel(hostel.getFloor(), hostel.getRoomNo(), hostel.getStudent());
            session.save(newHostel);
            transaction.commit();
            return true;
        }
        catch (HibernateException exception) {
            System.out.println("Hibernate Exception");
            System.out.print(exception.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public boolean updateRoom(Integer hostelId, Hostel newHostel) {
        try(Session session = HibernateSessionUtil.getSession()){
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("Update Hostel set student=:student where hostelId =: hostelId");
            query.setParameter("hostelId",hostelId);
            query.setParameter("student",newHostel.getStudent());
            query.executeUpdate();
            tx.commit();
            System.out.println(newHostel);
            session.saveOrUpdate(newHostel);
            session.close();
            System.out.println("Object Updated successfully.....!!");
            return true;
        }
        catch (HibernateException exception) {
            System.out.println("Hibernate Exception");
            System.out.print(exception.getLocalizedMessage());
            return false;
        }
    }

}
