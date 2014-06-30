package com.ebol4.libgdxgame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Floor {
    static ArrayList<Room> rooms = new ArrayList<Room>();
    public static void generateFloor() {
        List<Integer> roomIntersections;
        int indexOfClosestRoom;

        //Clear out any old rooms from the previous floor first, if any
        rooms.clear();
        rooms.add(new Room(Room.Type.NORMAL, 1500f, 1500f, 575f, 575f)); //Initial starting room in the middle of the map
        for(int i=0;i<5;i++ ) { //Randomly generate other rooms
            rooms.add(new Room(
                    Room.Type.NORMAL,     //type
                    (float) Math.random() * 3000 + 1, //x
                    (float) Math.random() * 3000 + 1, //y
                    (float) Math.random() * 1000 + 1000, //w
                    (float) Math.random() * 1000 + 1000  //h
            ));
        }
        while(!(roomIntersections = getRoomIntersections()).isEmpty()) {
            for(Iterator<Integer> intersectionIterator = roomIntersections.iterator(); intersectionIterator.hasNext(); ) {
                int roomIndex1 = intersectionIterator.next();
                int roomIndex2 = intersectionIterator.next();
                Room room1 = rooms.get(roomIndex1);
                Room room2 = rooms.get(roomIndex2);

                if(room1.getCenterX() <= room2.getCenterX()) {
                    float xDistanceToMove = (room1.getRightX() - room2.getLeftX())/2;
                    room1.x -= xDistanceToMove + (float)Math.random() * 100 + 100;
                    room2.x += xDistanceToMove + (float)Math.random() * 100 + 100;
                }
                else if(room1.getCenterX() > room2.getCenterX()) {
                    float xDistanceToMove = (room2.getRightX() - room1.getLeftX())/2;
                    room1.x += xDistanceToMove + (float)Math.random() * 100 + 100;
                    room2.x -= xDistanceToMove + (float)Math.random() * 100 + 100;
                }
            }
        }
        for(Room r: rooms) { //TODO: Merge this when it is no longer needed.
            r.updateVertices();
        }
        //intersections have been dealt with.... now for hallways

        int i = 0;
        for(Iterator<Room> roomIterator = rooms.iterator(); roomIterator.hasNext(); i++){
            if(i == 0) {
                //TODO: Maybe something more random
                for(int hallwaysToGenerate = 2; hallwaysToGenerate != 0; hallwaysToGenerate--) {
                    int whichWallToStartFrom = (int)(Math.random() * 4) + 1;
                    if (whichWallToStartFrom == 1)
                }
            }
            else{

            }
        }

     }


    //Returns an array containing the indices of rooms that intersect. [0] and [1] intersect, [2] and [3] intersect, and so on.
    //Therefore, this will always return a list with two or more indices if there were intersections,
    //or no indices if there were no intersections.
    private static List<Integer> getRoomIntersections() {
        List<Integer> returnArray = new ArrayList<Integer>();
        for(int i = 0; i < rooms.size(); i++ ) {
            Room room = rooms.get(i);
            for(int j = i+1; j < rooms.size(); j++) {
                if(room.getRectangle().overlaps(rooms.get(j).getRectangle())) {
                    returnArray.add(i);
                    returnArray.add(j);
                }
            }
        }
        return returnArray;
    }

}
