package ua.dragunovskiy.notes_mvc_1.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dragunovskiy.notes_mvc_1.entity.Note;
import ua.dragunovskiy.notes_mvc_1.entity.User;

import java.util.List;

@Repository
public class NoteDaoImpl implements NoteDao<Integer, Note> {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    @Transactional
    public List<Note> getAllNotes(int id) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, id);
        Query<Note> query = session.createQuery("from Note where user = :user", Note.class);
        query.setParameter("user", user);
        List<Note> resultList = query.getResultList();
        return resultList;
    }

    @Override
    @Transactional
    public void addNote(int userId, Note note) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, userId);
        user.getNoteList().add(note);
        session.merge(user);
    }


    @Override
    @Transactional
    public void updateNote(int userId, Note note) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, userId);
        user.getNoteList().set(note.getId(), note);
        session.merge(note);
    }

    @Override
    @Transactional
    public void deleteNote(int userId, int noteId) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, userId);
        List<Note> noteList = user.getNoteList();
        Note noteForDelete = findNoteById(noteList, noteId);
        user.getNoteList().remove(noteForDelete);
        session.remove(noteForDelete);
    }

    public Note findNoteById(List<Note> noteList, int noteId) {
        for (Note note : noteList) {
            if (note.getId() == noteId) {
                return note;
            }
        }
        return null;
    }
}
