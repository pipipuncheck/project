package com.example.jwtauth.infrastructure.repository;

import com.example.jwtauth.application.user.mapper.TicketMapper;
import com.example.jwtauth.domain.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TicketMapper ticketMapper;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate, TicketMapper ticketMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.ticketMapper = ticketMapper;
    }

    public void save(User user){
        String sql = "insert into users(username, email, password, role) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), user.getRole().toString());
    }

    public void save(Review review){
        String sql = "insert into reviews(user_id, artist_id, rating, comment) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, review.getUserId(), review.getArtistId(), review.getRating(), review.getComment());
    }

    public void save(Event event){
        String sql = "insert into events(name, artist_id, start_time, end_time, location, date) VALUES(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, event.getName(), event.getArtistId(), event.getStartTime(), event.getEndTime(), event.getLocation(), event.getDate());
    }

    public void save(Artist artist){
        String sql = "insert into artists(name, genre, description) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, artist.getName(), artist.getGenre(), artist.getDescription());
    }

    public Optional<User> findByUsername(String username){
        String sql = "select * from users where username=?";
        return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny();
    }

    public List<User> findAll(){
        String sql = "select * from users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
    public Optional<User> findOneById(int id){
        String sql = "select * from users where id=?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny();
    }

//    private List<Ticket> listOfTickets(){
//        String sql = "select * from events";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Event.class));
//    }

    private List<Event> listOfEvents(int userId) {
        String sql = "SELECT e.id, e.name, e.artist_id, e.start_time, e.end_time, e.location, e.date\n" +
                "FROM events e\n" +
                "JOIN tickets t ON e.id = t.event_id\n" +
                "WHERE t.user_id = ?\n";

        return jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(Event.class));
    }

    public List<Ticket> showAllTicketsByUsername(int userId){
        List<Event> events = listOfEvents(userId);

        return events
                .stream()
                .map(event -> {
                    Ticket ticket = getTicketIdByEventId(event.getId()).orElseThrow(() ->
                            new IllegalArgumentException("такого билета нет"));
                    Artist artist = findArtistById(event.getArtistId()).orElseThrow(() ->
                            new IllegalArgumentException("такого артиста нет"));
                    return ticketMapper.convertEventToTicket(event, ticket.getId(), artist);
                })
                .toList();

    }

    private Optional<Ticket> getTicketIdByEventId(int eventId){
        String sql = "select * from tickets where event_id = ?";
        return jdbcTemplate.query(sql, new Object[]{eventId}, new BeanPropertyRowMapper<>(Ticket.class))
                .stream().findAny();
    }


    public void makeUserAdmin(int userId){
        String sql = "update users set role = 'ADMIN' where id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public void makeUserManager(int userId){
        String sql = "update users set role = 'MANAGER' where id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public void deleteById(int userId){
        String sql = "delete from users where id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public List<Event> checkEventsByDate(LocalDate date){
        String sql = "select * from events where date = ?";
        return jdbcTemplate.query(sql, new Object[]{date}, new BeanPropertyRowMapper<>(Event.class));
    }

    public void makeTicket(int userId, int eventId){
        String sql = "insert into tickets(user_id, event_id) values(?, ?)";
        jdbcTemplate.update(sql, userId, eventId);
    }

    public Optional<Event> findEventByName(String name){
        String sql = "select * from events where name = ?";
        return jdbcTemplate.query(sql, new Object[]{name}, new BeanPropertyRowMapper<>(Event.class))
                .stream().findAny();
    }

    public Optional<Artist> findArtistById(int id){
        String sql = "select * from artists where id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Artist.class))
                .stream().findAny();
    }

    public Optional<Artist> findArtistByName(String name){
        String sql = "select * from artists where name = ?";
        return jdbcTemplate.query(sql, new Object[]{name}, new BeanPropertyRowMapper<>(Artist.class))
                .stream().findAny();
    }

    public List<Artist> showAllArtists() {
        String sql = "select * from artists";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Artist.class));
    }

    public boolean isTicketOrEventActive(Ticket ticket){


        LocalDate date = ticket.getDate();
        LocalDate currentDate = LocalDate.now();

        if (currentDate.isAfter(date))
            return false;
         else
            return true;

    }

    public boolean isTicketOrEventActive(Event event){

        LocalDate date =  event.getDate();

        LocalDate currentDate = LocalDate.now();

        if (currentDate.isAfter(date))
            return false;
        else
            return true;

    }



}
