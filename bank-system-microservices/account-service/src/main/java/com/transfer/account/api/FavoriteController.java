package com.transfer.account.api;

import com.transfer.account.dto.request.FavoriteAddRequest;
import com.transfer.account.dto.response.FavoriteResponse;
import com.transfer.account.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(
            summary = "Add a new favorite",
            description = "Adds a new favorite item to the user's account.",
            tags = {"Favorites"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Favorite added successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input"
            )
    })
    @PostMapping
    public ResponseEntity<FavoriteResponse> addFavorite(
            @RequestBody FavoriteAddRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(favoriteService.addFavorite(request));
    }

    @Operation(
            summary = "Remove a favorite",
            description = "Removes a favorite item from the user's account.",
            tags = {"Favorites"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Favorite removed successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Favorite not found"
            )
    })
    @DeleteMapping
    public ResponseEntity<String> removeFavorite(
            @NotNull(message = "favoriteId must be required")
            @RequestParam("favorite-id") int favoriteId) {
         favoriteService.removeFavorite(favoriteId);
        return ResponseEntity.ok(
                String.format("Favorite with ID:: %d removed successfully", favoriteId)
        );
    }

    @Operation(
            summary = "Get all favorites",
            description = "Retrieves all favorite items for a given account number.",
            tags = {"Favorites"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Favorites retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found"
            )
    })
    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getAllFavorites(
            @NotNull @RequestParam("account-number") String accountNumber
    ) {
        return ResponseEntity.ok(favoriteService.getAllFavorites(accountNumber));
    }
}
