package ru.miit.webapp.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.miit.webapp.dto.AddGroupDto;
import ru.miit.webapp.dto.ShowDetailsGroupInfoDto;
import ru.miit.webapp.dto.ShowGroupInfoDto;
import ru.miit.webapp.models.Group;
import ru.miit.webapp.repositories.GroupRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GroupService(GroupRepository groupRepository, ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
    }

    public void addGroup(AddGroupDto groupDto) {
        groupRepository.save(modelMapper.map(groupDto, Group.class));
    }

    public List<ShowGroupInfoDto> allGroups() {
        return groupRepository.findAll().stream()
                .map(group -> modelMapper.map(group, ShowGroupInfoDto.class))
                .collect(Collectors.toList());
    }

    public ShowDetailsGroupInfoDto groupInfo(String groupName) {
        return modelMapper.map(groupRepository.findByGroupName(groupName).orElse(null), ShowDetailsGroupInfoDto.class);
    }

    public void removeGroup(String groupName) {
        groupRepository.deleteByGroupName(groupName);
    }
}
