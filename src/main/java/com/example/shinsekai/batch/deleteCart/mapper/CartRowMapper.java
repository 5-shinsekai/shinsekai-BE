package com.example.shinsekai.batch.deleteCart.mapper;

import com.example.shinsekai.cart.entity.Cart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartRowMapper implements RowMapper<Cart> {
    @Override
    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Cart.builder()
                .id(rs.getLong("id"))
                .cartUuid(rs.getString("cart_uuid"))
                .memberUuid(rs.getString("member_uuid"))
                .productOptionListId(rs.getLong("product_option_list_id"))
                .productCode(rs.getString("product_code"))
                .quantity(rs.getInt("quantity"))
                .checked(rs.getBoolean("checked"))
                .engravingMessage(rs.getString("engraving_message"))
                .isFrozen(rs.getBoolean("is_frozen"))
                .isDeleted(rs.getBoolean("is_deleted"))
                .build();
    }
}

