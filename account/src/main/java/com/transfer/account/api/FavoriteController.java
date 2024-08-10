package com.transfer.account.api;

import com.transfer.account.dto.request.FavoriteAddRequest;
import com.transfer.account.dto.response.FavoriteResponse;
import com.transfer.account.service.FavoriteService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
@Validated
@CrossOrigin()
public class FavoriteController {

    private final FavoriteService favoriteService;


    @PostMapping("/add-favorite")
    public ResponseEntity<FavoriteResponse> addFavorite(
            @RequestBody FavoriteAddRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        favoriteService.addFavorite(request)
                );
    }

    @DeleteMapping("/delete-favorite")
    public ResponseEntity<String> removeFavorite(
            @NotNull(message = "favoriteId must be required")
            @RequestParam("favorite-id") int favoriteId) {
         favoriteService.removeFavorite(favoriteId);
        return ResponseEntity.ok(
                String.format("Favorite with ID:: %d removed successfully", favoriteId)
        );
    }

    @GetMapping("/get-favorites")
    public ResponseEntity<List<FavoriteResponse>> getAllFavorites(
            @NotNull @RequestParam("account-number") String accountNumber
    ) {
        return ResponseEntity.ok(favoriteService.getAllFavorites(accountNumber));
    }
}
