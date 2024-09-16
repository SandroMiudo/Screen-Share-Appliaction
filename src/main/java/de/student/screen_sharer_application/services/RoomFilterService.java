package de.student.screen_sharer_application.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RoomFilterService {

    private final IRoomRepo roomRepo;

    public RoomFilterService(IRoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    public List<RoomInfo> filterByFullRooms(){
        List<RoomInfo> allRooms = roomRepo.getAllRooms();
        return allRooms.stream().filter(x -> x.currentSize().equals(x.size()))
                .toList();
    }

    public List<RoomInfo> filterByNotFullRooms(){
        return roomRepo.getAllRooms().stream().filter(x -> x.currentSize() < x.size())
                .toList();
    }

    public List<RoomInfo> getRandom10Rooms(){
        List<RoomInfo> allRooms = roomRepo.getAllRooms();
        if(allRooms.size() <= 10) return allRooms;
        return allRooms.subList(0,10);
    }

    public List<RoomInfo> getRandom10JoinableRooms(){
        List<RoomInfo> allRooms = roomRepo.getAllRooms().stream().filter(x -> x.currentSize() < x.size())
                .toList();
        if(allRooms.size() <= 10) return allRooms;
        return allRooms.subList(0,10);
    }
}
