// 获取商品列表中的所有商品元素
const productElems = document.querySelectorAll('.product');

let total = 0; // 订单总额

// 为每个商品的加减按钮绑定事件处理函数
productElems.forEach(elem => {
    const minusBtn = elem.querySelector('.quantity-btn.minus');
    const plusBtn = elem.querySelector('.quantity-btn.plus');
    const quantityInput = elem.querySelector('.quantity-input');
    const subtotalElem = elem.querySelector('.subtotal');

    // 为数量减少按钮绑定事件处理函数
    minusBtn.addEventListener('click', () => {
        let count = parseInt(quantityInput.value);
        if (count > 0) {
            count--;
            quantityInput.value = count;
            subtotalElem.textContent = `小计：¥${(count * parseFloat(elem.querySelector('.product-price').textContent.replace('¥', ''))).toFixed(2)}`;
            total -= parseFloat(elem.querySelector('.product-price').textContent.replace('¥', ''));
            document.querySelector('.checkout-total').textContent = `${total.toFixed(2)}`;

            //点击-按钮时，对每一个按钮进行循环
            const goodsArr = [];//初始化购物数字符串空组，每次点击都要新的一轮计算
            productElems.forEach(elem => {
                const count = parseInt(elem.querySelector('.quantity-input').value);
                if (count > 0)
                {
                    const name = elem.querySelector('.product-name').textContent;
                    //计算价格
                    const price = parseFloat(elem.querySelector('.product-price').textContent.replace('¥', ''));
                    goodsArr.push(` ${name} x ${count}，单价：${price.toFixed(2)} `);//连接购物内容字符串
                }
            });
            document.querySelector('.checkout-info').textContent =goodsArr;//赋值给需要展示的地方
        }
    });

    // 为数量增加按钮绑定事件处理函数
    plusBtn.addEventListener('click', () => {
        let count = parseInt(quantityInput.value);
        count++;
        quantityInput.value = count;
        subtotalElem.textContent = `小计：¥${(count * parseFloat(elem.querySelector('.product-price').textContent.replace('¥', ''))).toFixed(2)}`;
        total += parseFloat(elem.querySelector('.product-price').textContent.replace('¥', ''));
        document.querySelector('.checkout-total').textContent = `${total.toFixed(2)}`;

        //点击+按钮时
        const goodsArr = [];//购物数组
        productElems.forEach(elem => {
            const count = parseInt(elem.querySelector('.quantity-input').value);
            if (count > 0)
            {
                const name = elem.querySelector('.product-name').textContent;
                //计算价格
                const price = parseFloat(elem.querySelector('.product-price').textContent.replace('¥', ''));
                goodsArr.push(` ${name} x ${count}，单价：${price.toFixed(2)} `);//连接购物内容字符串
            }
        });
        document.querySelector('.checkout-info').textContent =goodsArr;//赋值给需要展示的地方
    });

    // 设置初始状态下的商品小计为 0 元
    subtotalElem.textContent = '小计：¥0.00';
});


// 初始化结算部分的信息
document.querySelector('.checkout-total').textContent = '0.00';
document.querySelector('.checkout-info').textContent = '购物车为空';