<%--
    Document   : Quantity
    Created on : Feb 13, 2024, 9:40:46 AM
    Author     : Stupid User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="inline-block">
    <div class="flex">
        <div id="minus-btn" class="border aspect-square w-[40px] flex justify-center items-center rounded-l-md border-r-[0.5px] cursor-pointer">
            -
        </div>
        <input
            type="number"
            value="1"
            id="quantity-input"
            class="border border-x-[0.25px] w-[80px] flex justify-center items-center px-2"
            />
        <div id="plus-btn" class="border aspect-square w-[40px] flex justify-center items-center rounded-r-md border-l-[0.5px] cursor-pointer">
            +
        </div>
    </div>
</div>
