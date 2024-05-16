package com.javaweb.service.impl;

import com.javaweb.converter.ComboConverter;
import com.javaweb.converter.DishConverter;
import com.javaweb.converter.RestaurantConverter;
import com.javaweb.model.dto.*;
import com.javaweb.model.entity.ComboEntity;
import com.javaweb.model.entity.DishEntity;
import com.javaweb.model.entity.RestaurantEntity;
import com.javaweb.model.request.RestaurantSearchRequest;
import com.javaweb.repository.DishRepository;
import com.javaweb.repository.RestaurantRepository;
import com.javaweb.repository.ComboRepository;
import com.javaweb.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantConverter restaurantConverter;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishConverter dishConverter;

    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private ComboConverter comboConverter;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RestaurantDto> getRestaurantByParams(Map<String, Object> params, Integer customerPostcode) {
        RestaurantSearchRequest request = restaurantConverter.paramsToRequest(params);
        return restaurantRepository.findByNameContainsAndCategoryContains(request.getKeyword(), request.getCategory()).stream()
                .map(restaurantEntity -> restaurantConverter.entityToDto(restaurantEntity, customerPostcode))
                .filter(restaurantDto -> restaurantDto.getDistance() >= request.getDistanceFrom()
                        && restaurantDto.getDistance() <= request.getDistanceTo()
                        && restaurantDto.getStars() >= request.getRatingFrom()
                        && restaurantDto.getStars() <= request.getRatingTo())
                .collect(Collectors.toList());
    }

    @Override
    public boolean existRestaurant(String email, String password) {
        return restaurantRepository.existsByEmailAndPassword(email, password);
    }

    @Override
    public boolean existEmail(String email) {
        return restaurantRepository.existsByEmail(email);
    }

    @Override
    public void createRestaurant(RestaurantSignUpDto restaurantSignUpDto) {
        restaurantRepository.save(restaurantConverter.signUpDtoToEntity(restaurantSignUpDto));
    }

    @Override
    public void updateRestaurant(RestaurantUpdateDto restaurantUpdateDto) {
        RestaurantEntity restaurantEntity = restaurantConverter.updateDtoToEntity(restaurantUpdateDto);
        RestaurantEntity oldRestaurant = restaurantRepository.findById(restaurantUpdateDto.getId()).get();
        restaurantEntity.setEmail(oldRestaurant.getEmail());
        restaurantEntity.setPassword(oldRestaurant.getPassword());
        restaurantRepository.save(restaurantEntity);
    }

    @Override
    public void putBestSellers(List<Integer> dishIds, Integer restaurantId) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId).get();
        List<DishEntity> dishEntityList = restaurantEntity.getDishEntityList();
        dishEntityList.forEach(dishEntity -> {
            if (dishIds.contains(dishEntity.getId())) {
                dishEntity.setBestsellerStatus(true);
            }
        });
        restaurantRepository.save(restaurantEntity);
    }

    @Override
    public void createDish(DishDto dishDto) {
        DishEntity dishEntity = dishConverter.dtoToEntity(dishDto);
        dishEntity.setId(null);
        dishEntity.setRestaurantEntity(restaurantRepository.findById(dishDto.getRestaurantId()).get());
        dishRepository.save(dishEntity);
    }

    @Override
    public void updateDish(DishDto dishDto) {
        DishEntity oldDish = dishRepository.findById(dishDto.getId()).get();
        DishEntity newDish = dishConverter.dtoToEntity(dishDto);
        newDish.setId(oldDish.getId());
        newDish.setRestaurantEntity(oldDish.getRestaurantEntity());
        dishRepository.save(newDish);
    }

    @Override
    public DishDto findDishById(Integer dishId) {
        DishEntity dishEntity = dishRepository.findById(dishId).get();
        return dishConverter.entityToDto(dishEntity);
    }

    @Override
    public ComboDto findComboById(Integer comboId) {
        ComboEntity comboEntity = comboRepository.findById(comboId).get();
        return comboConverter.entityToDto(comboEntity);
    }

    @Override
    public void createCombo(ComboDto comboDto) {
        ComboEntity comboEntity = comboConverter.dtoToEntity(comboDto);
        comboEntity.setId(null);
        comboEntity.setRestaurantEntity(restaurantRepository.findById(comboDto.getRestaurantId()).get());
        comboRepository.save(comboEntity);
    }

    @Override
    public void updateCombo(ComboDto comboDto) {
        ComboEntity oldCombo = comboRepository.findById(comboDto.getId()).get();
        ComboEntity newCombo = comboConverter.dtoToEntity(comboDto);
        newCombo.setId(oldCombo.getId());
        newCombo.setRestaurantEntity(oldCombo.getRestaurantEntity());
        comboRepository.save(newCombo);
    }

    @Override
    public RestaurantProfileDto findRestaurantProfileById(Integer restaurantId) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId).get();

        RestaurantDto restaurantDto = restaurantConverter.entityToDto(restaurantEntity, 0);
        List<DishDto> dishDtoList = restaurantEntity.getDishEntityList().stream()
                .map(dishConverter::entityToDto)
                .collect(Collectors.toList());
        List<ComboDto> comboDtoList = restaurantEntity.getComboEntityList().stream()
                .map(comboConverter::entityToDto)
                .collect(Collectors.toList());

        RestaurantProfileDto restaurantProfileDto = modelMapper.map(restaurantEntity, RestaurantProfileDto.class);
        restaurantProfileDto.setRestaurantDto(restaurantDto);
        restaurantProfileDto.setDishDtoList(dishDtoList);
        restaurantProfileDto.setComboDtoList(comboDtoList);

        restaurantProfileDto.setBestsellerDishDtoList(restaurantEntity.getDishEntityList().stream()
                .filter(DishEntity::isBestsellerStatus)
                .map(dishConverter::entityToDto)
                .collect(Collectors.toList()));
        return restaurantProfileDto;
    }

    @Override
    public Integer getRestaurantIdByEmail(String email) {
        return restaurantRepository.findByEmail(email).getId();
    }
}