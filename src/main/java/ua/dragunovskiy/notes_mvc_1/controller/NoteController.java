package ua.dragunovskiy.notes_mvc_1.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dragunovskiy.notes_mvc_1.entity.Note;
import ua.dragunovskiy.notes_mvc_1.entity.User;
import ua.dragunovskiy.notes_mvc_1.service.NoteService;
import ua.dragunovskiy.notes_mvc_1.service.UserService;

import java.util.List;

@Controller
public class NoteController {

    @Autowired
    NoteService<Integer, Note> noteService;

    @Autowired
    UserService<Integer, User> userService;

    @GetMapping("/notes/{id}")
    public String getAllNotes(@PathVariable("id") int id,  Model model) {
        User user = userService.getById(id);
        model.addAttribute("allNotes", noteService.getAllNotes(id));
        model.addAttribute("user", user);
        return "/note/getAllNotes";
    }

    @GetMapping("/notes/new/{id}")
    public String addNote(@PathVariable("id") int id,  Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("newNote", new Note());
        return "/note/addNote";
    }

    @PostMapping("/notes/add/{id}")
    public String addNote(@PathVariable("id") int id, @Valid @ModelAttribute("newNote") Note note, BindingResult bindingResult) {
        User user = userService.getById(id);
        if (user != null) {
            note.setUser(user);
            if (bindingResult.hasFieldErrors("name")) {
                return "/error/noteErrorName";
            } else if (bindingResult.hasFieldErrors("content")) {
                return "/error/noteErrorContent";
            }
            user.getNoteList().add(note);
            userService.add(user);
        }
        return "redirect:/users";
    }

    @GetMapping("/notes/edit/{userId}/{noteId}")
    public String updateNote(@PathVariable("userId") int userId, @PathVariable("noteId") int noteId, Model model) {
        User user = userService.getById(userId);
        if (user != null) {
            List<Note> noteList = user.getNoteList();
            Note noteToUpdate = noteService.findNoteById(noteList, noteId);
            if (noteToUpdate != null) {
                model.addAttribute("user", user);
                model.addAttribute("noteToUpdate", noteToUpdate);
                return "/note/updateNote";
            }
        }
        return "redirect:/users";
    }

    @PostMapping("/notes/update/{userId}/{noteId}")
    public String updateNote(@PathVariable("userId") int userId, @PathVariable("noteId") int noteId, @Valid @ModelAttribute("noteToUpdate") Note noteToUpdate, BindingResult bindingResult) {
        User user = userService.getById(userId);
        if (user != null) {
            List<Note> noteList = user.getNoteList();
            noteToUpdate.setUser(user);
            Note updatedNote = noteService.findNoteById(noteList, noteId);
            if (updatedNote != null) {
                if (bindingResult.hasFieldErrors("name")) {
                    return "/error/noteErrorNameUpdate";
                } else if (bindingResult.hasFieldErrors("content")) {
                    return "/error/noteErrorContentUpdate";
                }
                updatedNote.setName(noteToUpdate.getName());
                updatedNote.setContent(noteToUpdate.getContent());
                userService.update(user);
            }
        }
        return "redirect:/users";
    }

    @RequestMapping("/notes/delete/{userId}/{noteId}")
    public String deleteNote(@PathVariable("userId") int userId, @PathVariable("noteId") int noteId) {
        noteService.deleteNote(userId, noteId);
        return "redirect:/notes/{userId}";
    }
}