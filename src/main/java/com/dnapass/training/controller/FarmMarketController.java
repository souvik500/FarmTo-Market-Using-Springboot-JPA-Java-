package com.dnapass.training.controller;

import com.dnapass.training.entity.*;
import com.dnapass.training.service.EquipmentService;
import com.dnapass.training.service.ItemService;
import com.dnapass.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/farmmarket")
public class FarmMarketController
{
    @Autowired
    private UserService userService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private ItemService itemService;



    //Mapping with /login with `request-body login
    @PostMapping("/login")
    public ResponseEntity<Object> checkLogin(@RequestBody Login login) {
        User user = userService.findUser(login.getUserid());
        if (user != null && user.getPassword().equals(login.getPassword())) {
            login.setRole(user.getRole());
            return ResponseEntity.ok(login);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //Mapping with "/new-user"
    @PostMapping("/newuser")
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User newUser = userService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);

    }

    //Mapping with "/search" based on city parameter
    @GetMapping("/search")
    public ResponseEntity<List<Equipment>> farmerSearchEquipment(@RequestParam("city") String city)
    {

        List<Equipment> equipmentList = equipmentService.searchEquipment(city);
        return new ResponseEntity<>(equipmentList, HttpStatus.OK);
    }

    //Mapping with "/getAllEquipment"
    @GetMapping("/getAllEquipment")
    public ResponseEntity<List<Equipment>> getAllEquipment()
    {
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        return new ResponseEntity<>(equipmentList, HttpStatus.OK);
    }

    //Mapping with "/getAllEquipmentById/{id} based on user id
    @GetMapping("/getAllEquipmentById/{id}")
    public ResponseEntity<List<Equipment>> getAllEquipmentById(@PathVariable("id")Integer id) {
        List<Equipment> equipmentList = equipmentService.getAllEquipmentById(id);
        return new ResponseEntity<>(equipmentList, HttpStatus.OK);
    }

    //Mapping with "/getAllItemById/{id}" based on user id
    @GetMapping("/getAllItemById/{id}")
    public ResponseEntity<List<Item>> getAllItemById(@PathVariable("id")Integer id) {
        List<Item> itemList = itemService.getAllItemById(id);
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    // Mapping wih "/book/{equipId}/{userid}/{quantitycount}" to book equipment based on equipment id, user id and number of quantity
    @PutMapping("/book/{equipId}/{userid}/{quantitycount}")
    public ResponseEntity<Equipment> bookEquipment(@PathVariable("equipId") Integer id,
                                                   @PathVariable("quantitycount") Integer ecount,
                                                   @PathVariable("userid")Integer uid)
    {
        Equipment equipmentToBook = equipmentService.getEquipmentById(id);

        if (equipmentToBook == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (equipmentToBook.getCount() < ecount) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        equipmentToBook.setCount(equipmentToBook.getCount() - ecount);
        Equipment bookedEquipment = equipmentService.addEquipment(equipmentToBook);

//        if (bookedEquipment == null) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        // Create a new EquipmentUser instance to store the booking details
        EquipmentUser equipmentUser = new EquipmentUser();
        equipmentUser.setEquipment(bookedEquipment);
        equipmentUser.setUser(userService.findUser(uid));

        // Add the booking details to the database
        EquipmentUser addedEquipmentUser = equipmentService.addEquipmentUser(equipmentUser);

//        if (addedEquipmentUser == null) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        return new ResponseEntity<>(bookedEquipment, HttpStatus.OK);
    }

    //Mapping with "/addNewItem/{id}" based on particular userid with Item body
    @PostMapping("/addNewItem/{id}")
    public ResponseEntity<Item> addNewItem(@RequestBody Item item, @PathVariable("id") Integer userId)
    {
        User user = userService.findUser(userId);
        item.setUser(user);

        Item addedItem = itemService.addNewItem(item);

//        if (addedItem == null) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    // Mapping with "/getAllItems" to return all items for traders to view
    @GetMapping("/getAllItems")
    public ResponseEntity<List<ItemDetails>> getAllItems() {
        List<ItemDetails> itemDetailsList = itemService.getAllItems();
        return new ResponseEntity<>(itemDetailsList, HttpStatus.OK);
    }

    // Mapping with "/deleteItemDetails/{itemId}" to delete item based on itemId
    @DeleteMapping("/deleteItemDetails/{itemId}")
    public ResponseEntity<HttpStatus> deleteItemDetails(@PathVariable("itemId") Integer itemId) {
        itemService.deleteItemDetails(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Mapping with "/itemDetails/{uid}" to update the items based on particular user with body Item
    @PutMapping("/itemDetails/{uid}")
    public ResponseEntity<Item> updateItemDetails(@PathVariable("uid") Integer userId,
                                                  @RequestBody Item item)
    {
        User user = userService.findUser(userId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Item currentItem = itemService.getItemById(item.getItemId());

        if (currentItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!currentItem.getUser().getId().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        currentItem.setItemName(item.getItemName());
        currentItem.setItemDescription(item.getItemDescription());
        currentItem.setItemQuantity(item.getItemQuantity());

        Item savedItem = itemService.addNewItem(currentItem);

        if (savedItem == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(savedItem, HttpStatus.OK);
    }

    //Mapping with "/addNewEquipment/{id}" to add new equipment for particular userid with equipment body
    @PostMapping("/addNewEquipment/{id}")
    public ResponseEntity<Equipment> addNewEquipment(@RequestBody Equipment equipment,
                                                     @PathVariable("id") Integer userId)
    {
        User user = userService.findUser(userId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        equipment.setUser(user);

        Equipment savedEquipment = equipmentService.addEquipment(equipment);

        if (savedEquipment == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(savedEquipment, HttpStatus.CREATED);
    }

    //Mapping with "/getAllEquipments" to display all equipment for farmer based on count
    @GetMapping("/getAllEquipments")
    public ResponseEntity<List<Equipment>> getAllEquipments() {
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        return new ResponseEntity<>(equipmentList, HttpStatus.OK);
    }

    //Mapping with "/deleteEquipmentDetails/{equipId}" to delete particular equipment based on equipment id
    @DeleteMapping("/deleteEquipmentDetails/{equipId}")
    public ResponseEntity<HttpStatus> deleteEquipmentDetails(@PathVariable("equipId") Integer equipId) {
        equipmentService.deleteEquipmentDetail(equipId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Mapping with "/equipmentDetails/{uid}" to update equipment detail based on particular user with equipment body
    @PutMapping("/equipmentDetails/{uid}")
    public ResponseEntity<Equipment> updateEquipmentDetails(
            @PathVariable("uid") Integer userId, @RequestBody Equipment equipment)
    {

        Equipment existingEquipment = equipmentService.getEquipmentById(userId);
        if (existingEquipment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the equipment details
        existingEquipment.setName(equipment.getName());
        existingEquipment.setCount(equipment.getCount());
        existingEquipment.setRentPerDay(equipment.getRentPerDay());
        existingEquipment.setEState(equipment.getEState());
        existingEquipment.setECity(equipment.getECity());
        existingEquipment.setVillage(equipment.getVillage());
        existingEquipment.setEPinCode(equipment.getEPinCode());
        existingEquipment.setContactPerson(equipment.getContactPerson());
        existingEquipment.setEMobileNumber(equipment.getEMobileNumber());

        Equipment updatedEquipment = equipmentService.addEquipment(existingEquipment);
        if (updatedEquipment == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(updatedEquipment, HttpStatus.OK);
    }

    //Mapping with "/returnEquipment" to return an equipment and update the count
    @PutMapping("/returnEquipment")
    public ResponseEntity<Equipment> returnEquipment(@RequestBody EquipmentDetails edetails)
    {
        Equipment equipment = equipmentService.getEquipmentById(edetails.getEquipmentId());

        if (equipment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        equipment.setCount(equipment.getCount() + edetails.getQuantityCount());

        equipmentService.deleteEquipmentUser(edetails.getBookingId());

        Equipment updatedEquipment = equipmentService.addEquipment(equipment);

        if (updatedEquipment == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(updatedEquipment, HttpStatus.OK);
    }

    //Mapping with "/getHiredEquipment/{id}" to view hired equipment based on booking id
    @GetMapping("/getHiredEquipment/{id}")
    public ResponseEntity<List<EquipmentDetails>> getHiredEquipment(@PathVariable("id")Integer id) {
        List<EquipmentDetails> hiredEquipmentList = equipmentService.getHiredEquipment(id);
        return new ResponseEntity<>(hiredEquipmentList, HttpStatus.OK);
    }

    //Mapping with "/searchItem" to search the item by trader based on city
    @GetMapping("/searchItem")
    public ResponseEntity<List<ItemDetails>> traderSearchItem(@RequestParam("city") String city) {
        List<ItemDetails> searchedItemList = itemService.searchItem(city);
        return new ResponseEntity<>(searchedItemList, HttpStatus.OK);
    }

    // Mapping with "/getFarmer/{id}" to display all items for that farmer based on userid
    @GetMapping("/getFarmer/{id}")
    public ResponseEntity<User> getFarmerDetails(@PathVariable("id") Integer id)
    {
        User farmer = userService.findUser(id);

        if (farmer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(farmer, HttpStatus.OK);
    }

    //Mapping with "/orderItem/{itemId}" by trader based on itemid and update the count of items
    @PutMapping("/orderItem/{itemId}")
    public ResponseEntity<ItemDetails> orderItem(@PathVariable("itemId") Integer id)
    {
        Item item = itemService.getItemById(id);
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (item.getItemQuantity() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        item.setItemQuantity(item.getItemQuantity() - 1);
        Item updatedItem = itemService.addNewItem(item);
        if (updatedItem == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ItemDetails updatedItemDetails = new ItemDetails();
        updatedItemDetails.setItemId(updatedItem.getItemId());
        updatedItemDetails.setItemName(updatedItem.getItemName());
        updatedItemDetails.setItemQuantity(updatedItem.getItemQuantity());
        updatedItemDetails.setUsername(updatedItem.getUser().getUsername());
        updatedItemDetails.setMobileNumber(updatedItem.getUser().getMobileNumber());
        updatedItemDetails.setId(updatedItem.getUser().getId());

        return new ResponseEntity<>(updatedItemDetails, HttpStatus.OK);
    }
}

