package com.javaweb.api;

import com.javaweb.model.dto.*;
import com.javaweb.model.response.MessageResponse;
import com.javaweb.model.response.RestaurantLoginResponse;
import com.javaweb.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")

// due to tight deadlines, we cannot implement "putting dishes in a combo",
// so for now just treat a combo as a "dish" placed in the "combo" table
// and pretend the table dish_combo doesn't exist
public class RestaurantApi {

    @Autowired
    RestaurantService restaurantService;


    /*
* Request body example
*
* {
    "email": "restaurant_49@gmail.com",
    "password": "123456"
  }
  *
  *
  * response body example
  *
  * {
        "message": "Restaurant owner logged in",
        "customerId": 149 //use this to do other operations, like updating restaurant info
    }
* */
    @PostMapping(value = "/login")
    public ResponseEntity<RestaurantLoginResponse> login(@RequestBody RestaurantLoginDto restaurantLoginDto) {
        if (!restaurantService.existRestaurant(restaurantLoginDto.getEmail(), restaurantLoginDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON).
                    body(new RestaurantLoginResponse("Wrong email or password", null));
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestaurantLoginResponse("Restaurant owner logged in", restaurantService.getRestaurantIdByEmail(restaurantLoginDto.getEmail())));
    }


    /*
    * request body example
    *
    * {
        "email": "john@gmail.com",
        "password": "123456"
      }
    * */
    @PutMapping(value = "/signup")
    public ResponseEntity<MessageResponse> signup(@RequestBody RestaurantSignUpDto restaurantSignUpDto) {
        if (restaurantService.existEmail(restaurantSignUpDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON).
                    body(new MessageResponse("Email already registered"));
        }
        restaurantService.createRestaurant(restaurantSignUpDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON).
                body(new MessageResponse("Restaurant registered"));
    }


    /* request body example
    * {
        "id": 110,
        "name": "The Great Restaurant",
        "address": "123 Main St",
        "postCode": 12345,
        "startTime": "08:00:00",
        "endTime": "22:00:00",
        "phone": "123-456-7890",
        "category": "mexican"
      }
    * */
    @PostMapping(value = "/info")
    public ResponseEntity<MessageResponse> updateInfo(@RequestBody RestaurantUpdateDto restaurantUpdateDto) {
        restaurantService.updateRestaurant(restaurantUpdateDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageResponse("Restaurant info updated"));
    }


    @PostMapping(value = "/food/bestsellers")
    // url example:
    // http://localhost:8081/restaurants/food/bestsellers?dishId=10,11,12,13&id=101
    public ResponseEntity<MessageResponse> putBestSellers(@RequestParam(name = "dishId") List<Integer> dishIds,
                                                          @RequestParam(name = "id") Integer restaurantId) {
        restaurantService.putBestSellers(dishIds, restaurantId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON).
                body(new MessageResponse("Restaurant info updated"));
    }

    // requestbody example
    /*/
        {
            "restaurantId": 120, //use the restaurantId gotten from login() here
            "name": "Pasta",
            "description": "Delicious pasta with tomato sauce",
            "price": 12.99,
            "image": "encoded string" // encode your uploaded image in the frontend into a string (base64), and put the string here
        }

     */
    @PutMapping(value = "/food/dish")
    public ResponseEntity<MessageResponse> addDish(@RequestBody DishDto dishDto) {
        restaurantService.createDish(dishDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON).
                body(new MessageResponse("Dish added"));
    }

    // use this to paste the old data that you want to update to the figma design page 26
    // url example: http://localhost:8081/restaurants/food/dish?dishId=10
    @GetMapping(value = "/food/dish")
    public DishDto getDish(@RequestParam Integer dishId) {
        return restaurantService.findDishById(dishId);
    }

    /*
    request body example
    {
    "id": 1,
    "restaurantId": 101, // use the restaurantId gotten from login() here
    "name": "Pasta",
    "description": "Delicious pasta with tomato sauce",
    "price": 12.99,
    "image": ""
}
    * */
    @PostMapping(value = "/food/dish")
    public ResponseEntity<MessageResponse> updateDish(@RequestBody DishDto dishDto) {
        restaurantService.updateDish(dishDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageResponse("Dish updated"));
    }


    /*
    *
    *
    *   {
            "restaurantId": 120, //use the restaurantId gotten from login() here
            "name": "Pasta combo",
            "description": "Delicious pasta with tomato sauce and shits",
            "price": 12.99,
            "image": "encoded string" // encode your uploaded image in the frontend into a string (base64), and put the string here
        }
    *
    *  */
    @PutMapping(value = "/food/combo")
    public ResponseEntity<MessageResponse> addCombo(@RequestBody ComboDto comboDto) {
        restaurantService.createCombo(comboDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON).
                body(new MessageResponse("Combo added"));
    }

    @GetMapping(value = "/food/combo")
    // use this to paste the old data of the combo that you want to update (no page for this in figma yet)
    // url example: http://localhost:8081/restaurants/food/combo?id=10
    public ComboDto getCombo(@RequestParam(name = "id") Integer comboId) {
        return restaurantService.findComboById(comboId);
    }


    // request body example
    /*
    * {
        "id": 10,
        "restaurantId": 101, //use the restaurantId gotten from login() here
        "name": "shitty Combo",
        "description": "This is Combo shits",
        "price": 44.63,
        "image": ""
      }
* */
    @PostMapping(value = "/food/combo")
    public ResponseEntity<MessageResponse> updateCombo(@RequestBody ComboDto comboDto) {
        restaurantService.updateCombo(comboDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageResponse("Combo updated"));
    }


    /*
     * url example
     *
     * http://localhost:8081/restaurants/profile?id=102
     * */
    @GetMapping(value = "/profile")
    public RestaurantProfileDto getRestaurantProfile(@RequestParam(name = "id") Integer restaurantId) {
        return restaurantService.findRestaurantProfileById(restaurantId);
    }

}
