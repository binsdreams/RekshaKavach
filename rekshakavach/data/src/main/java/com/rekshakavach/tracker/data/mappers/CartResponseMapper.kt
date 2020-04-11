package com.rekshakavach.tracker.data.mappers

import com.rekshakavach.tracker.data.entities.CartItemResponse
import com.rekshakavach.tracker.data.entities.CartResponse
import com.rekshakavach.tracker.data.entities.Response
import com.rekshakavach.tracker.domain.entity.CartEntity
import com.rekshakavach.tracker.domain.entity.CartItemEntity

class CartResponseMapper {

    private val profileMapper = ProfileResponseMapper()
    private val orderResponseMapper = OrderResponseMapper()

    fun mapCartEntity(response: Response<CartResponse>): CartEntity =
        mapCartEntity(response.message, response.status, response.data)

    private fun mapCartEntity(message: String?, status: Int, response: CartResponse?): CartEntity =
        CartEntity(
            id = response?.id,
            express_delivery = response?.express_delivery,
            delivery_time = response?.delivery_time,
            pickup_time = response?.pickup_time,
            agent_details = orderResponseMapper.mapAgent(response?.agent_details),
            agent_id = response?.agent_id,
            cart_discount = response?.cart_discount,
            cart_net_price = response?.cart_net_price,
            cart_status = response?.cart_status,
            cart_step_progress = response?.cart_step_progress,
            cart_total_price = response?.cart_total_price,
            cart_unique_id = response?.cart_unique_id,
            convenience_fee = response?.convenience_fee,
            created_at = response?.created_at,
            express_delivery_charges = response?.express_delivery_charges,
            payment_mode = response?.payment_mode,
            updated_at = response?.updated_at,
            user_cart_item = mapCartItemList(response?.user_cart_item),
            user_delivery_address = profileMapper.mapAddress(response?.user_delivery_address),
            user_id = response?.user_id,
            user_pickup_address = profileMapper.mapAddress(response?.user_pickup_address),
            vat = response?.vat
        )

    private fun mapCartItemList(userCartItem: List<CartItemResponse>?)
            : List<CartItemEntity> = userCartItem?.map {
        mapCartItemEntity(it)
    } ?: emptyList()

    private fun mapCartItemEntity(response: CartItemResponse): CartItemEntity =
        CartItemEntity(
            id = response.id,
            user_id = response.user_id,
            cart_id = response.cart_id,
            cloth_charge_per_piece = response.cloth_charge_per_piece,
            cloth_count = response.cloth_count,
            cloth_total_price = response.cloth_total_price,
            cloth_type_details = orderResponseMapper.mapCategory(response.cloth_type_details),
            cloth_type_id = response.cloth_type_id,
            discount_per_piece = response.discount_per_piece,
            net_price = response.net_price,
            service_details = orderResponseMapper.mapService(response.service_details),
            service_id = response.service_id
        )
}