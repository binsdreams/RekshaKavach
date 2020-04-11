package com.rekshakavach.tracker.data.mappers

import com.rekshakavach.tracker.data.entities.*
import com.rekshakavach.tracker.domain.entity.*

class OrderResponseMapper {

    fun mapOrderListEntity(response: Response<List<OrderResponse>>): OrderListEntity =
        mapOrderListEntiity(response.message, response.status, response.data)

    private fun mapOrderListEntiity(
        message: String?,
        status: Int,
        response: List<OrderResponse>?
    ): OrderListEntity = OrderListEntity(
        message = message,
        status = status,
        ordersList = mapOrderList(response)
    )

    private fun mapOrderList(orderList: List<OrderResponse>?)
            : List<OrderEntity> = orderList?.map {
        mapOrder(it)
    } ?: emptyList()

    private fun mapOrder(response: OrderResponse?): OrderEntity = OrderEntity(
        id = response?.id,
        order_unique_id = response?.order_unique_id,
        agent_id = response?.agent_id,
        user_id = response?.user_id,
        order_status = response?.order_status,
        order_total_price = response?.order_total_price,
        created_at = response?.created_at,
        updated_at = response?.updated_at,
        agent_details = mapAgent(response?.agent_details),
        pickup_time = response?.pickup_time,
        delivery_time = response?.delivery_time,
        express_delivery = response?.express_delivery
    )

    fun mapAgent(response: AgentResponse?): AgentEntity = AgentEntity(
        id = response?.id,
        agent_name = response?.agent_name,
        desc = response?.desc,
        mobile = response?.mobile,
        media_url = response?.media_url,
        featured_image_url = response?.featured_image_url,
        location = response?.location
    )

    fun mapCategoriesListEntity(response: Response<List<CategoryResponse>>): CategoriesListEntity? =
        mapCategoriesListEntiity(response.message, response.status, response.data)

    private fun mapCategoriesListEntiity(
        message: String?,
        status: Int,
        response: List<CategoryResponse>?
    ): CategoriesListEntity = CategoriesListEntity(
        message = message,
        status = status,
        categoriesList = mapCategoriesList(response)
    )

    private fun mapCategoriesList(catList: List<CategoryResponse>?)
            : List<CategoryEntity> = catList?.map {
        mapCategory(it)
    } ?: emptyList()

    fun mapCategory(response: CategoryResponse?): CategoryEntity = CategoryEntity(
        id = response?.id,
        category_name = response?.category_name,
        parent_category_id = response?.parent_category_id,
        url = response?.url,
        subCategories = mapCategoriesList(response?.sub)
    )

    fun mapServicesListEntity(response: Response<List<ServiceResponse>>): ServicesListEntity? =
        mapServicesListEntity(response.message, response.status, response.data)

    private fun mapServicesListEntity(
        message: String?,
        status: Int,
        response: List<ServiceResponse>?
    ): ServicesListEntity = ServicesListEntity(
        message = message,
        status = status,
        servicesList = mapServicesList(response)
    )

    private fun mapServicesList(servicesList: List<ServiceResponse>?)
            : List<ServiceEntity> = servicesList?.map {
        mapService(it)
    } ?: emptyList()

    fun mapService(response: ServiceResponse?): ServiceEntity = ServiceEntity(
        id = response?.id,
        service_name = response?.service_name,
        desc = response?.desc
    )
}