package com.myApp.web.service.Implementation;

import com.myApp.web.dto.ClubDto;
import com.myApp.web.mapper.ClubMapper;
import com.myApp.web.models.Club;
import com.myApp.web.repository.ClubRepository;
import com.myApp.web.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.myApp.web.mapper.ClubMapper.mapToClub;
import static com.myApp.web.mapper.ClubMapper.mapToClubDto;


@Service
public class ClubServiceImpl implements ClubService {

    private ClubRepository clubRepository;

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public List<ClubDto> fidAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map((ClubMapper::mapToClubDto)).collect(Collectors.toList());
    }

    @Override
    public Club saveClub(ClubDto club) {
        return clubRepository.save(mapToClub(club));
    }

    @Override
    public ClubDto findClubById(long clubId) {
        Club club = clubRepository.findById(clubId).get();
        return mapToClubDto(club);
    }

    @Override
    public void updateClub(ClubDto clubDto) {
        Club club = mapToClub(clubDto);
        clubRepository.save(club);
    }

    @Override
    public void delete(long clubId) {
        clubRepository.deleteById(clubId);
    }

    @Override
    public List<ClubDto> searchClubs(String query) {
        List<Club> clubs = clubRepository.searchClubs(query);
        return clubs.stream().map(ClubMapper::mapToClubDto).collect(Collectors.toList());
    }


}