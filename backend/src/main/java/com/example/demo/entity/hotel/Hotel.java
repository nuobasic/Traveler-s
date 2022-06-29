package com.example.demo.entity.hotel;


import com.example.demo.dto.hotel.HotelConvert;
import com.example.demo.entity.member.User;
import com.example.demo.entity.room.Room;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@Data
@Entity
@ToString(exclude = {"rooms", "user"})
@Table(name = "hotel")
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelNo;

    @Column(length = 20, nullable = false)
    private String hotelName;

    @Column(name = "hotelInfo")
    private String hotelInfo;

    @Column(length = 128, nullable = false)
    private String hotelIntro;

    @Column(nullable = false)
    private String postcode;

    @Column(nullable = false)
    private String totalAddress;

    @Column(nullable = false) // default 255
    private String hotelImgPath1;
    @Column(nullable = false)
    private String hotelImgPath2;
    @Column(nullable = false)
    private String hotelImgPath3;
    @Column(nullable = false)
    private String hotelImgPath4;
    @Column(nullable = false)
    private String hotelImgPath5;
    @Column
    private String hotelImgPath6;
    @Column
    private String hotelImgPath7;
    @Column
    private String hotelImgPath8;
    @Column
    private String hotelImgPath9;

    @JsonManagedReference
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    @Column
    private String openKakaotalk;

    @CreationTimestamp
    private Date regDate;

    @UpdateTimestamp
    private Date updDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    public void addUserToHotel(User user){
        if (this.user != null){
            this.user.getHotels().remove(this);
        }
        this.user = user;
        this.user.getHotels().add(this);
    }

    public List<String> getHotelInfo() {
        List<String> list = new ArrayList<>();
        if (hotelInfo != null) {
            Arrays.stream(hotelInfo.split(","))
                    .map(String::trim)
                    .filter(s -> !"".equals(s))
                    .forEach(list::add);
        }
        return list;
    }

    public void setHotelInfo(List<String> hotelInfo) {
        this.hotelInfo = treeAsString(hotelInfo);
    }

    private static String treeAsString(List<String> hotelInfo) {
        List<String> orderedSet = new ArrayList<>();
        if (hotelInfo != null && !hotelInfo.isEmpty()) {
            orderedSet.addAll(hotelInfo);
        }
        return "," + String.join(",", orderedSet) + ",";
    }

    public static class HotelBuilder {

        private String hotelInfo;

        public HotelBuilder hotelInfo(List<String> hotelInfo) {
            this.hotelInfo = treeAsString(hotelInfo);
            return this;
        }

    }

    public Hotel(String hotelName, String hotelIntro, String hotelInfo, String totalAddress, String postcode,
                 String hotelImgPath1, String hotelImgPath2, String hotelImgPath3, String hotelImgPath4, String hotelImgPath5) {
        this.hotelName = hotelName;
        this.hotelIntro = hotelIntro;
        this.hotelInfo = hotelInfo;
        this.postcode = postcode;
        this.totalAddress = totalAddress;
        this.hotelImgPath1 = hotelImgPath1;
        this.hotelImgPath2 = hotelImgPath2;
        this.hotelImgPath3 = hotelImgPath3;
        this.hotelImgPath4 = hotelImgPath4;
        this.hotelImgPath5 = hotelImgPath5;
    }

}