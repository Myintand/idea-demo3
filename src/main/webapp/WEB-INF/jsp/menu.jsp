<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>菜谱</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu.css ">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/find.css ">
</head>
<%--顶部--%>
<div class="indexnavigation" >
  <a href="${pageContext.request.contextPath}/StoreController/showstores" title="返回店铺列表">返回</a>
</div>
<body>
<span></span>
<div class="ingredient-search">
  <p style="display: inline-block; font-size: 24px; font-weight: bold; color: #333;">我有什么？</p>
  <img src="${pageContext.request.contextPath}/pictures/小四.jpg" class="img2" style="display: inline-block;">
  <hr>
  <input type="checkbox" name="ingredient" value="鸡蛋">鸡蛋
  <input type="checkbox" name="ingredient" value="茄子">茄子
  <input type="checkbox" name="ingredient" value="黄瓜">黄瓜
  <input type="checkbox" name="ingredient" value="西红柿">西红柿
  <input type="checkbox" name="ingredient" value="土豆">土豆
  <input type="checkbox" name="ingredient" value="排骨">排骨
  <input type="checkbox" name="ingredient" value="小龙虾">小龙虾
  <input type="checkbox" name="ingredient" value="秋葵">秋葵
  <input type="checkbox" name="ingredient" value="鲫鱼">鲫鱼
  <input type="checkbox" name="ingredient" value="牛蛙">牛蛙
  <input type="checkbox" name="ingredient" value="西葫芦">西葫芦
  <input type="checkbox" name="ingredient" value="青椒">青椒
  <input type="checkbox" name="ingredient" value="牛肉">牛肉
  <input type="checkbox" name="ingredient" value="胡萝卜">胡萝卜
  <input type="checkbox" name="ingredient" value="豆腐">豆腐
  <input type="checkbox" name="ingredient" value="猪肉">猪肉
  <input type="checkbox" name="ingredient" value="鸡腿">鸡腿
</div>

<div class="search-box">
  <input type="text" id="search-input" placeholder="请输入搜索关键词">
  <button onclick="searchRecipes()" type="submit">搜索</button>
</div>

<p style="font-size: 20px">${message2}</p>

<div class="recipes">

  <div class="collect">
    <p style="text-align: center;font-size: 35px">我的收藏</p>
    <p style="font-size: 20px;color: green">${message}</p>
    <c:forEach var="menu" items="${menuList}">
      <tr>
        <td style="font-size: 30px">
            ${menu}
              <button class="delete-payment-btn" onclick="window.location.href='${pageContext.request.contextPath}/MenuController/delete?recipeName=${menu}'"><i class="fa fa-trash"></i></button>
              <br><br>
        </td>
      </tr>
    </c:forEach>
  </div>


  <div class="recipe-card">
    <h3>西红柿炒蛋</h3>
    <img src="${pageContext.request.contextPath}/pictures/西红柿炒蛋.png" class="img1">
    <p>关键食材：西红柿，鸡蛋</p>
    <div class="details">
      <p>详细做法：</p>
      <ol>
        <li>西红柿洗净改刀切小块。</li>
        <li>鸡蛋加少许盐打散。</li>
        <li>热锅温油，倒入打散的蛋液，摇匀一圈，让蛋液充分散开。</li>
        <li>迅速用锅铲戳散翻炒均匀，盛出备用。</li>
        <li>西红柿炒到出汁水，倒入鸡蛋，继续翻炒均匀。</li>
        <li>调入少许盐调味，关火。</li>
      </ol>
    </div>
    <p class="show-button">查看做法</p>
    <br>
    <button class="collect-button" onclick="collectRecipe('西红柿炒蛋')">收藏菜谱</button>
  </div>

  <div class="recipe-card">
    <h3>家常红烧茄子</h3>
    <img src="${pageContext.request.contextPath}/pictures/茄子.jpg" class="img1">
    <p>关键食材：茄子，青椒</p>
    <div class="details">
      <p>详细做法：</p>
      <ol>
        <li>茄子洗净切滚刀块后均匀的撒上些盐腌制10-20分钟。</li>
        <li>青椒和西红柿清洗干净后切块。</li>
        <li>大蒜若干一部分切片，一部分切末。</li>
        <li>将腌制好的茄子用力攥一下水分，这样可以让茄子少吃油。</li>
        <li>平底锅热后放适量的花生油，然后放蒜瓣爆香。</li>
        <li>闻到蒜香后放入攥干水分的茄子，中火翻炒至茄子变色、变软。</li>
        <li>放青椒和西红柿，加一勺白糖和适量的盐调味，大火翻炒均匀，中途可以少加一点热水炒制西红柿变软出现红色汤汁。</li>
        <li>出锅前撒上切好的蒜末。</li>
      </ol>
    </div>
    <p class="show-button">查看做法</p>
    <br>
    <button class="collect-button" onclick="collectRecipe('家常红烧茄子')">收藏菜谱</button>
  </div>

  <div class="recipe-card">
    <h3>拍黄瓜</h3>
    <img src="${pageContext.request.contextPath}/pictures/黄瓜.jpg" class="img1">
    <p>关键食材：黄瓜</p>
    <div class="details">
      <p>详细做法：</p>
      <ol>
        <li>黄瓜洗净放在菜板上，用切菜刀平拍，使黄瓜开裂。</li>
        <li>切成小块。</li>
        <li>大蒜剁碎。</li>
        <li>把拍好的黄瓜和大蒜放入盘中，加入盐香油鸡精和少量的醋搅拌均匀即可。</li>
      </ol>
    </div>
    <p class="show-button">查看做法</p>
    <br>
    <button class="collect-button" onclick="collectRecipe('拍黄瓜')">收藏菜谱</button>
  </div>

  <div class="recipe-card">
    <h3>土豆炖牛肉</h3>
    <img src="${pageContext.request.contextPath}/pictures/土豆炖牛肉.jpg" class="img1">
    <p>关键食材：牛肉，土豆，胡萝卜</p>
    <div class="details">
      <p>详细做法：</p>
      <ol>
        <li>牛肉洗净，整块放凉水里上锅，开盖焯。</li>
        <li>滚开后捞出用热水洗净（注意整个炖肉过程都不要用凉水，肉一激就不容易烂了）。</li>
        <li>切小块，最好放在漏盆里充分沥干。</li>
        <li>上锅放油。油三成热放葱段姜片红辣椒，炒香了放肉，迅速翻炒。</li>
        <li>肉断生了放生抽老抽冰糖，继续翻炒。</li>
        <li>自己觉得炒的差不多了，倒开水至把肉没了。大火滚开2分钟，连汤带肉倒进电压力锅（我不敢用放火上烧的高压锅）。放一包炖肉料，放自己觉得足够多的盐。（如果用普通高压锅就是开盖之后放盐）。电压力锅30分钟，出锅。</li>
        <li>土豆胡萝卜切块，锅里放少许油，油半热后小火把土豆胡萝卜煸一下（用油煸过的胡萝卜有营养，土豆容易炖软不易粘锅）。放入若干牛肉和汤，比例自己搭配。依照汤的咸度决定是否放盐。小火炖至土豆软面，大火收汁儿。装盘，放香菜。</li>
      </ol>
    </div>
    <p class="show-button">查看做法</p>
    <br>
    <button class="collect-button" onclick="collectRecipe('土豆炖牛肉')">收藏菜谱</button>
  </div>

  <div class="recipe-card">
    <h3>粉蒸排骨</h3>
    <img src="${pageContext.request.contextPath}/pictures/排骨.jpg" class="img1">
    <p>关键食材：排骨，土豆</p>
    <div class="details">
      <p>详细做法：</p>
      <ol>
        <li>排骨斩小块放清水里泡去血水。</li>
        <li>加糖、生抽、老抽、姜、蒜、白胡椒粉、蚝油料酒拌匀。</li>
        <li>拌匀后腌制约30分钟。</li>
        <li>拌入蒸肉米粉。</li>
        <li>再加少许水或高汤拌匀待用。</li>
        <li>土豆去皮削块，加少许盐拌匀，将排骨铺在土豆上面。</li>
        <li>高压锅的话20几分钟左右。趁热吃。</li>
      </ol>
    </div>
    <p class="show-button">查看做法</p>
    <br>
    <button class="collect-button" onclick="collectRecipe('粉蒸排骨')">收藏菜谱</button>
  </div>

  <div class="recipe-card">
    <h3>麻辣小龙虾</h3>
    <img src="${pageContext.request.contextPath}/pictures/小龙虾.jpg" class="img1">
    <p>关键食材：小龙虾</p>
    <div class="details">
      <p>详细做法：</p>
      <ol>
        <li>先将小龙虾用刷子把外壳刷洗干净。</li>
        <li>再用剪刀把龙虾头里的沙囊去掉。</li>
        <li>再用手指尖掐住龙虾尾巴中间的位置往外拉，沙线便轻易取出。</li>
        <li>准备好洗净的龙虾和调味料。</li>
        <li>准备小葱和姜片。</li>
        <li>锅中上油（油要多些），油烧热后下入砂糖。</li>
        <li>倒入小龙虾，中火翻炒（在倒龙虾时，注意别烫到手）。</li>
        <li>把小龙虾炒到变红出水时，放入葱和姜片翻炒片刻。</li>
        <li>倒入步骤5里的调味料和辣椒粉翻炒。</li>
        <li>炒出香味后加入料酒、生抽、老抽。</li>
        <li>醋和鸡精翻炒均匀</li>
        <li>加入半锅清水，加盐。</li>
        <li>翻炒均匀后盖盖，中火烧15到20分钟。</li>
        <li>把料汁收至半干即可。</li>
      </ol>
    </div>
    <p class="show-button">查看做法</p>
    <br>
    <button class="collect-button" onclick="collectRecipe('麻辣小龙虾')">收藏菜谱</button>
  </div>

  <div class="recipe-card">
    <h3>秋葵炒鸡蛋</h3>
    <img src="${pageContext.request.contextPath}/pictures/秋葵.jpg" class="img1">
    <p>关键食材：秋葵，鸡蛋</p>
    <div class="details">
      <p>详细做法：</p>
      <ol>
        <li>秋葵清洗干净，切开。</li>
        <li>鸡蛋磕开后，加点盐打散。</li>
        <li>锅中放适量的菜油，倒入秋葵先炒。</li>
        <li>待秋葵基本炒熟，划到一边，再倒入鸡蛋液，炒。</li>
        <li>然后将二者炒在一起，要炒散了。然后出锅装盘。</li>
      </ol>
    </div>
    <p class="show-button">查看做法</p>
    <br>
    <button class="collect-button" onclick="collectRecipe('秋葵炒鸡蛋')">收藏菜谱</button>
  </div>

  <div class="recipe-card">
    <h3>鲫鱼豆腐汤</h3>
    <img src="${pageContext.request.contextPath}/pictures/鲫鱼豆腐.jpg" class="img1">
    <p>关键食材：鲫鱼，豆腐</p>
    <div class="details">
      <p>详细做法：</p>
      <ol>
        <li>鲫鱼洗净。</li>
        <li>平底锅烧热，放入少量油，将鲫鱼放入。</li>
        <li>煎至两面呈金黄色，（煎的过程中火力不能小，要中火、中大火相互转换为宜，将两面煎成金黄色），依次加入大葱片、姜片。</li>
        <li>加入热水。</li>
        <li>加入料酒。</li>
        <li>大火烧开后继续煮7、8分钟，转中小火煮20分钟左右。</li>
        <li>最后加入豆腐煮熟。</li>
        <li>出锅前调入盐，撒上葱花即可（鱼汤本身就很鲜，所以不建议再加鸡精或味精）。</li>
      </ol>
    </div>
    <p class="show-button">查看做法</p>
    <br>
    <button class="collect-button" onclick="collectRecipe('鲫鱼豆腐汤')">收藏菜谱</button>
  </div>

  <div class="recipe-card">
    <h3>干锅牛蛙</h3>
    <img src="${pageContext.request.contextPath}/pictures/牛蛙.jpg" class="img1">
    <p>关键食材：牛蛙</p>
    <div class="details">
      <p>详细做法：</p>
      <ol>
        <li>牛蛙洗净后斩成大块。</li>
        <li>将牛蛙加入盐、料酒、水淀粉抓匀，腌制20分钟。</li>
        <li>姜去皮切片，蒜去皮略拍，香葱、青红椒洗净切小圈。</li>
        <li>锅置火上适量油烧热，爆香大蒜、姜、花椒，依次调入火锅底料、郫县豆瓣酱炒出红油。</li>
        <li>倒入牛蛙翻炒断生。</li>
        <li>加入少许砂糖、生抽，调味。</li>
        <li>转至小火，盖上锅盖焖烧5分钟至汤汁收少。</li>
        <li>转至干锅中，调入青红椒、白芝麻、淋入少许香油，便可趁热吃。</li>
      </ol>
    </div>
    <p class="show-button">查看做法</p>
    <br>
    <button class="collect-button" onclick="collectRecipe('干锅牛蛙')">收藏菜谱</button>
  </div>

  <div class="recipe-card">
    <h3>西葫芦炒肉片</h3>
    <img src="${pageContext.request.contextPath}/pictures/西葫芦.jpg" class="img1">
    <p>关键食材：西葫芦，猪肉</p>
    <div class="details">
      <p>详细做法：</p>
      <ol>
        <li>准备好西葫芦和猪肉。</li>
        <li>将猪肉切片。</li>
        <li>西葫芦洗净一开四，然后切片。</li>
        <li>起油锅，爆香蒜头，下肉片煸炒片刻，下点生抽入入味。</li>
        <li>炒好的肉片盛起待用。</li>
        <li>锅里加入适量的油加热，下西葫芦煸炒。</li>
        <li>待西葫芦炒软后，加入炒好的肉片。</li>
        <li>将肉片和西葫芦炒匀，沿锅边炝入一点清水，加适量的盐调味就行了。</li>
      </ol>
    </div>
    <p class="show-button">查看做法</p>
    <br>
    <button class="collect-button" onclick="collectRecipe('西葫芦炒肉片')">收藏菜谱</button>
  </div>

  <div class="recipe-card">
    <h3>青椒炒肉片</h3>
    <img src="${pageContext.request.contextPath}/pictures/青椒炒肉.png" class="img1">
    <p>关键食材：青椒，猪肉</p>
    <div class="details">
      <p>详细做法：</p>
      <ol>
        <li>准备好猪肉、青椒、葱姜。</li>
        <li>把青椒切片。</li>
        <li>把猪肉切片。</li>
        <li>将肉片用料酒、盐腌渍10分钟，然后用水淀粉抓匀上浆。</li>
        <li>起锅热油、爆香葱姜。</li>
        <li>放入浆好的肉片，用中火将其滑熟。</li>
        <li>烹入生抽。</li>
        <li>放入青椒片。</li>
        <li>旺火快炒，加盐、味精调味。炒至均匀即可。</li>
      </ol>
    </div>
    <p class="show-button">查看做法</p>
    <br>
    <button class="collect-button" onclick="collectRecipe('青椒炒肉片')">收藏菜谱</button>
  </div>

  <div class="recipe-card">
    <h3>口水鸡</h3>
    <img src="${pageContext.request.contextPath}/pictures/口水鸡.jpg" class="img1">
    <p>关键食材：鸡腿</p>
    <div class="details">
      <p>详细做法：</p>
      <ol>
        <li>鸡腿冷水下锅，加入葱，姜，料酒，几粒花椒。</li>
        <li>煮开撇去浮沫，转中小火煮15分钟，关火焖8分钟。</li>
        <li>捞出鸡腿，放入冰水净泡15分钟。</li>
        <li>准备白芝麻，两勺辣椒面，一勺花椒面。</li>
        <li>起锅烧油，加入葱姜，八角，桂皮，香叶，红椒，爆出香味。</li>
        <li>炸好的油过滤，倒入辣椒面里面。</li>
        <li>搅拌均匀，辣椒油搞好。</li>
        <li>酱汁：一勺陈醋，一勺蚝油，两勺生抽，一勺白糖，少许盐，少许胡椒粉，搅拌均匀。</li>
        <li>捞出鸡腿，擦干表面的水分，剁成块。</li>
        <li>剁好的鸡腿摆盘，先倒入酱汁，让鸡腿入味。</li>
        <li>剁点蒜末，花生碎，葱花。</li>
        <li>撒上花生碎，蒜末，花生碎，浇上调好的辣椒油，可以开吃了。</li>
      </ol>
    </div>
    <p class="show-button">查看做法</p>
    <br>
    <button class="collect-button" onclick="collectRecipe('口水鸡')">收藏菜谱</button>
  </div>





  <!-- 其他菜谱卡片 -->
  <!-- ... -->
</div>
<div id="pagination"></div>

<script>
  const checkboxes = document.querySelectorAll('input[name="ingredient"]');
  checkboxes.forEach(checkbox => checkbox.addEventListener('change', filterRecipes));

  const recipeCards = document.querySelectorAll('.recipe-card');
  recipeCards.forEach(card => {
    const showButton = card.querySelector('.show-button');
    const details = card.querySelector('.details');
    showButton.addEventListener('click', toggleDetails);

    function toggleDetails() {
      details.style.display = details.style.display === 'none' ? 'block' : 'none';
      showButton.textContent = details.style.display === 'none' ? '查看做法' : '收起';
    }
  });

  function filterRecipes() {
    const selectedIngredients = Array.from(checkboxes).filter(checkbox => checkbox.checked).map(checkbox => checkbox.value);

    recipeCards.forEach(card => {
      const keyIngredients = card.querySelector('p').textContent.split('关键食材：')[1].split('，');
      const display = selectedIngredients.length === 0 || keyIngredients.some(keyIngredient => selectedIngredients.includes(keyIngredient)) ? 'block' : 'none';
      card.style.display = display;
    });
  }

  // 定义每页最大显示数量和总页数
  const pageSize = 10;
  const totalRecipes = document.querySelectorAll('.recipe-card').length;
  const totalPages = Math.ceil(totalRecipes / pageSize);

  // 显示指定页码的菜谱
  function displayRecipes(page) {
    const recipeCards = document.querySelectorAll('.recipe-card');
    const startIndex = (page - 1) * pageSize;
    const endIndex = page * pageSize;

    recipeCards.forEach((card, index) => {
      if (index >= startIndex && index < endIndex) {
        card.style.display = 'block';
      } else {
        card.style.display = 'none';
      }
    });
  }

  // 创建页码按钮并绑定点击事件
  function createPaginationButtons() {
    const paginationContainer = document.getElementById('pagination');

    for (let i = 1; i <= totalPages; i++) {
      const button = document.createElement('button');
      button.textContent = i;

      button.addEventListener('click', () => {
        displayRecipes(i);
      });

      paginationContainer.appendChild(button);
    }
  }

  // 初始化页面，显示第一页的菜谱
  displayRecipes(1);

  // 创建页码按钮
  createPaginationButtons();

  function collectRecipe(recipeName) {
    window.location.href = "${pageContext.request.contextPath}/MenuController/addcollect?recipeName=" + encodeURIComponent(recipeName);
  }

  function searchRecipes() {
    // 获取搜索词
    var keyword = document.getElementById('search-input').value.toLowerCase();

    // 遍历每个菜谱卡片
    var recipeCards = document.getElementsByClassName('recipe-card');
    for (var i = 0; i < recipeCards.length; i++) {
      var card = recipeCards[i];
      var recipeName = card.getElementsByTagName('h3')[0].innerText.toLowerCase();

      // 判断是否匹配搜索词
      if (recipeName.includes(keyword)) {
        card.style.display = 'block'; // 显示该菜谱卡片
      } else {
        card.style.display = 'none'; // 隐藏该菜谱卡片
      }
    }
  }
</script>
</body>
</html>
