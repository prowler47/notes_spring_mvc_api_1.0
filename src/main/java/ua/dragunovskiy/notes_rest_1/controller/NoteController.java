package ua.dragunovskiy.notes_rest_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.dragunovskiy.notes_rest_1.dao.NoteDaoImpl;
import ua.dragunovskiy.notes_rest_1.dao.UserDaoImpl;
import ua.dragunovskiy.notes_rest_1.entity.Note;
import ua.dragunovskiy.notes_rest_1.entity.User;

import java.util.List;

@Controller
public class NoteController {

    @Autowired
    NoteDaoImpl noteDao;

    @Autowired
    UserDaoImpl userDao;

    @GetMapping("/notes/{id}")
    public String getAllNotes(@PathVariable("id") int id,  Model model) {
        User user = userDao.getById(id);
        model.addAttribute("allNotes", noteDao.getAllNotes(id));
        model.addAttribute("user", user);
        return "/note/getAllNotes";
    }

    @GetMapping("/notes/new/{id}")
    public String addNote(@PathVariable("id") int id,  Model model) {
        User user = userDao.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("newNote", new Note());
        return "/note/addNote";
    }

    @PostMapping("/notes/add/{id}")
    public String addNote(@PathVariable("id") int id, @ModelAttribute("newNote") Note note) {
        User user = userDao.getById(id);
        if (user != null) {
            note.setUser(user);
            user.getNoteList().add(note);
            userDao.add(user);
        }
        return "redirect:/users";
    }

    @GetMapping("/notes/edit/{userId}/{noteId}")
    public String updateNote(@PathVariable("userId") int userId, @PathVariable("noteId") int noteId, Model model) {
        User user = userDao.getById(userId);
        if (user != null) {
            List<Note> noteList = user.getNoteList();
            Note noteToUpdate = noteDao.findNoteById(noteList, noteId);
            if (noteToUpdate != null) {
                model.addAttribute("user", user);
                model.addAttribute("noteToUpdate", noteToUpdate);
                return "/note/updateNote";
            }
        }
        return "redirect:/users";
    }

    @PostMapping("/notes/update/{userId}/{noteId}")
    public String updateNote(@PathVariable("userId") int userId, @PathVariable("noteId") int noteId, @ModelAttribute("noteToUpdate") Note updatedNote) {
        User user = userDao.getById(userId);
        if (user != null) {
            List<Note> noteList = user.getNoteList();
            Note noteToUpdate = noteDao.findNoteById(noteList, noteId);
            if (noteToUpdate != null) {
                noteToUpdate.setName(updatedNote.getName());
                noteToUpdate.setContent(updatedNote.getContent());
                userDao.update(user);
            }
        }
        return "redirect:/users";
    }

    @RequestMapping("/notes/delete/{userId}/{noteId}")
    public String deleteNote(@PathVariable("userId") int userId, @PathVariable("noteId") int noteId) {
        noteDao.deleteNote(userId, noteId);
        return "redirect:/notes/{userId}";
    }
}
