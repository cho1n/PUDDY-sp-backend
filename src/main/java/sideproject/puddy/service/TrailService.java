package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.trail.TrailDto;
import sideproject.puddy.model.Person;
import sideproject.puddy.model.Trail;
import sideproject.puddy.repository.TrailRepository;
import sideproject.puddy.security.util.SecurityUtil;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrailService {
    private final TrailRepository trailRepository;
    private final AuthService authService;
    public List<TrailDto> getNearTrails(){
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        List<TrailDto> trails = findAll();
        Double lat = person.getLatitude();
        Double lng = person.getLongitude();
        List<TrailDto> nearTrails = new ArrayList<>();
        for (TrailDto trail : trails){
            Double midLat = (trail.getStartLat() + trail.getEndLat()) / 2;
            Double midLng = (trail.getStartLong() + trail.getEndLong()) / 2;
            if (distance(lat, lng, midLat, midLng) <= 3000){
                nearTrails.add(trail);
            }
        }
        return nearTrails;
    }
    public List<TrailDto> findAll(){
        return trailRepository.findAll().stream().map(trail -> new TrailDto(trail.getId(), trail.getName(), trail.getStartLat(), trail.getStartLong(), trail.getEndLat(), trail.getEndLong())).toList();
    }
    public  Double distance(double lat1, double lng1, double lat2, double lng2){
        Double theta = lng1 - lng2;
        Double dist = Math.sin(deg2rad(lat1))* Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60*1.1515*1609.344;

        return dist; //단위 meter
    }
    private Double deg2rad(Double deg){
        return (deg * Math.PI/180.0);
    }
    private Double rad2deg(Double rad){
        return (rad * 180 / Math.PI);
    }
    public double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double latDiff = lat2 - lat1;
        double lonDiff = lng2 - lng1;
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }
    public Trail findById(Long id){
        return trailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 산책로 입니다."));
    }
}
