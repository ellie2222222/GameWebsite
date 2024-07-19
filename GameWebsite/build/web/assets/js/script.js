document.addEventListener("DOMContentLoaded", function () {
    const minVal = document.querySelector(".min-val");
    const maxVal = document.querySelector(".max-val");
    const priceInputMin = document.querySelector(".min-input");
    const priceInputMax = document.querySelector(".max-input");

    function slideMin() {
        let gap = parseInt(maxVal.value) - parseInt(minVal.value);
        if (gap <= 1) {
            minVal.value = parseInt(maxVal.value) - 1;
        }
        priceInputMin.value = minVal.value;
    }

    function slideMax() {
        let gap = parseInt(maxVal.value) - parseInt(minVal.value);
        if (gap <= 1) {
            maxVal.value = parseInt(minVal.value) + 1;
        }
        priceInputMax.value = maxVal.value;
    }

    function setMinInput() {
        let minPrice = parseInt(priceInputMin.value);
        if (minPrice < minVal.min) {
            priceInputMin.value = minVal.min;
        }
        minVal.value = priceInputMin.value;
        slideMin();
    }

    function setMaxInput() {
        let maxPrice = parseInt(priceInputMax.value);
        if (maxPrice > maxVal.max) {
            priceInputMax.value = maxVal.max;
        }
        maxVal.value = priceInputMax.value;
        slideMax();
    }

    const games = document.querySelectorAll(".search-items li");

    //FILTER GAME BY PRICE
    function filterGamesByPriceRange() {
        const minPrice = parseInt(minVal.value);
        const maxPrice = parseInt(maxVal.value);

        games.forEach(function (game) {
            let gamePrice = game.querySelector(".item-price-box p").innerText;
            gamePrice = (gamePrice === "Free") ? 0 : parseInt(gamePrice.slice(1));

            game.style.display = (gamePrice >= minPrice && gamePrice <= maxPrice) ? "block" : "none";
        });
    }

    // FILTER GAME BY TITLE
    const searchInput = document.querySelector(".search-bar-items input");
    function filterGameByName() {
        const string = searchInput.value;
        games.forEach(function (game) {
            const gameTitle = game.querySelector(".item-title p").innerText;

            game.style.display = gameTitle.toLowerCase().includes(string.toLowerCase()) ? "block" : "none";
        });
    }

    // FILTER GENRE CHECKBOX BY INPUT
    const searchGenreInput = document.getElementById("searchGenre");
    const checkboxes = document.querySelectorAll(".checkbox-wrapper");
    function searchGenre() {
        const string = searchGenreInput.value;

        checkboxes.forEach(function (checkbox) {
            const labelValue = checkbox.querySelector(".genreLabel").innerText;

            checkbox.style.display = labelValue.toLowerCase().includes(string.toLowerCase()) ? "block" : "none";
        });
    }

    // FILTER GENRE BY CHECKBOX
    const checkboxGenre = document.querySelectorAll(".checkboxGenre");
    if (checkboxGenre !== null) {
        function filterGenre() {
            var checkboxList = [];

            checkboxGenre.forEach((checkbox) => {
                let wrapper = checkbox.closest(".checkbox-wrapper");
                genreValue = wrapper.querySelector(".genreLabel").innerText;

                if (checkbox.checked) {
                    checkboxList.push(genreValue);
                }
            });

            games.forEach(function (game) {
                const gameGenre = game.querySelector(".item-genre p").innerText;
                const matchesGenre = checkboxList.every((genre) => gameGenre.toLowerCase().includes(genre.toLowerCase()));
                game.style.display = matchesGenre ? "block" : "none";
                if (checkboxList.length === 0) {
                    game.style.display = "block";
                }
            });
        }

        checkboxGenre.forEach((checkbox) => {
            checkbox.addEventListener("click", filterGenre);
        });
    }

    // Format description
    let paragraph = document.getElementById("description");
    if (paragraph !== null) {
        const formattedText = paragraph.innerText.replace(/\\n/g, '\n');
        paragraph.innerText = formattedText;
        let sections = paragraph.innerText.split('\n');
        let firstSection = sections[0].trim();
        document.getElementById("overview").innerText = firstSection;
    }


    // Format date
    var dateElements = document.querySelectorAll('[id^="date"]');
    if (dateElements !== null) {
        dateElements.forEach(function (dateElement) {
            // Get the date string from the element
            var dateString = dateElement.innerText;

            // Convert the date string to a Date object
            var date = new Date(dateString);
            // Format the date as "day month, year"
            var formattedDate = formatDate(date);

            // Edit the content of the element with the formatted date
            dateElement.innerText = formattedDate;
        });
    }

    function formatDate(date) {
        var options = {day: 'numeric', month: 'long', year: 'numeric'};
        return date.toLocaleDateString('en-US', options);
    }
    
    //format list
    var bracketElements = document.querySelectorAll('[id^="genre"]');
    if (bracketElements !== null) {
        bracketElements.forEach(function (bracketElements) {
            // Get the date string from the element
            var listString = bracketElements.innerText;

            // Format the date as "day month, year"
            var formattedDate = removeSquareBrackets(listString);

            // Edit the content of the element with the formatted date
            bracketElements.innerText = formattedDate;
        });
    }
    
    function removeSquareBrackets(str) {
        return str.replace(/\[|\]/g, '');
    }


    // Attach event listeners
    var accountBtn = document.querySelector(".account-btn");
    if (accountBtn !== null) {
        accountBtn.addEventListener("click", accountDropdown);
    }

    if (minVal !== null) {
        minVal.addEventListener("input", slideMin);
        minVal.addEventListener("input", filterGamesByPriceRange);
    }

    if (maxVal !== null) {
        maxVal.addEventListener("input", filterGamesByPriceRange);
        maxVal.addEventListener("input", slideMax);
    }

    if (priceInputMin !== null) {
        priceInputMin.addEventListener("change", filterGamesByPriceRange);
        priceInputMin.addEventListener("change", setMinInput);
    }

    if (priceInputMax !== null) {
        priceInputMax.addEventListener("change", filterGamesByPriceRange);
        priceInputMax.addEventListener("change", setMaxInput);
    }

    if (searchInput !== null) {
        searchInput.addEventListener("input", filterGameByName);
    }

    if (searchGenreInput !== null) {
        searchGenreInput.addEventListener("input", searchGenre);
    }
});

//LOGIN / SIGNUP
function displayLogin(visibility) {
    const signInDiv = document.querySelector(".sign-in");
    const signUpDiv = document.querySelector(".sign-up");
    if (visibility) {
        signInDiv.style.display = "flex";
        signUpDiv.style.display = "none";
    } else {
        signUpDiv.style.display = "flex";
        signInDiv.style.display = "none";
    }
}

// TOGGLE BACKDROP
function toggleBackdrop(show) {
    var backdrop = document.querySelector('.backdrop');
    backdrop.style.display = show ? 'block' : 'none';
}


//ADD PRODUCT
function addProduct(event) {
    event.stopPropagation();
    var addBox = document.querySelector('.item-add');

    // Toggle visibility
    if (addBox.style.display === 'none' || addBox.style.display === '') {
        addBox.style.display = 'block';
        toggleBackdrop(true);
        document.addEventListener('click', clickOutside);
    }

    function clickOutside(event) {
        if (!addBox.contains(event.target)) {
            addBox.style.display = 'none';
            toggleBackdrop(false);
            document.removeEventListener('click', clickOutside);
        }
    }
}

//DELETE PRODUCT
function deleteProduct(element, event) {
    event.stopPropagation();
    var itemLi = element.closest('li');
    var deleteBox = itemLi.querySelector('.item-delete');

    // Toggle visibility
    if (deleteBox.style.display === 'none' || deleteBox.style.display === '') {
        deleteBox.style.display = 'flex';
        toggleBackdrop(true);
        document.addEventListener('click', clickOutside);
    } else {
        deleteBox.style.display = 'none';
        toggleBackdrop(false);
        document.removeEventListener('click', clickOutside);
    }

    function clickOutside(event) {
        if (!deleteBox.contains(event.target)) {
            deleteBox.style.display = 'none';
            toggleBackdrop(false);
            document.removeEventListener('click', clickOutside);
        }
    }
}

// EDIT PRODUCT
function displayItemEdit(element) {
    var itemLi = element.closest('li');
    var itemEdit = itemLi.querySelector(".item-edit");

    if (itemEdit.style.height === '0px' || itemEdit.style.height === '') {
        itemEdit.style.height = 'auto';
        itemEdit.style.padding = '10px';
    } else {
        itemEdit.style.height = '0px';
        itemEdit.style.padding = '0px';
    }
}

// DISPLAY CHECKOUT CONFIRMATION
function displayCheckout(event) {
    const itemCheckout = document.querySelector(".item-checkout");
    event.stopPropagation();

    // Toggle visibility
    if (itemCheckout.style.display === 'none' || itemCheckout.style.display === '') {
        itemCheckout.style.display = 'flex';
        toggleBackdrop(true);
        document.addEventListener('click', clickOutside);
    } else {
        itemCheckout.style.display = 'none';
        toggleBackdrop(false);
        document.removeEventListener('click', clickOutside);
    }

    function clickOutside(event) {
        if (!itemCheckout.contains(event.target)) {
            itemCheckout.style.display = 'none';
            toggleBackdrop(false);
            document.removeEventListener('click', clickOutside);
        }
    }
}


// CHECK PASSWORD MATCH
function checkPasswordMatch() {
    var password = document.getElementById('signup-pass').value;
    var retypePassword = document.getElementById('signup-repass').value;
    var messageElement = document.getElementById('password-match-message');

    if (password === retypePassword) {
        messageElement.textContent = '';
    } else {
        messageElement.textContent = 'Passwords do not match.';
        messageElement.style.color = 'red';
    }
}



document.addEventListener("DOMContentLoaded", function () {
    // MAIN SLIDESHOW
    let slideIndex = 1;
    showMainSlides(slideIndex);

    // Next/previous controls
    function plusMainSlides(n) {
        showMainSlides(slideIndex += n);
    }

    function showMainSlides(n) {
        let i;
        let slides = document.getElementsByClassName("main-slideshow");
        if (n > slides.length) {
            slideIndex = 1;
        }
        if (n < 1) {
            slideIndex = slides.length;
        }
        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        slides[slideIndex - 1].style.display = "block";
    }

    var prev = document.querySelector(".main-prev");
    var next = document.querySelector(".main-next");
    if (prev !== null) {
        prev.addEventListener("click", function () {
            plusMainSlides(-1);
        });
    }
    if (next !== null) {
        next.addEventListener("click", function () {
            plusMainSlides(1);
        });
    }
    
    // GENRE SLIDESHOW
    let genreSlideIndex = 1;
    showGenreSlides(genreSlideIndex);

    // Next/previous controls
    function plusGenreSlides(n) {
        showGenreSlides(genreSlideIndex += n);
    }

    function showGenreSlides(n) {
        let i;
        let slides = document.getElementsByClassName("genre-slideshow");
        if (n > slides.length) {
            genreSlideIndex = 1;
        }
        if (n < 1) {
            genreSlideIndex = slides.length;
        }
        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        slides[genreSlideIndex - 1].style.display = "flex";
    }

    var prev = document.querySelector(".genre-prev");
    var next = document.querySelector(".genre-next");
    if (prev !== null) {
        prev.addEventListener("click", function () {
            plusGenreSlides(-1);
        });
    }
    if (next !== null) {
        next.addEventListener("click", function () {
            plusGenreSlides(1);
        });
    }
});


document.addEventListener("DOMContentLoaded", function() {
    var orderDetailWrappers = document.querySelectorAll('.item-order-wrapper');

    orderDetailWrappers.forEach(function(wrapper) {
        wrapper.addEventListener('click', function(event) {
            var ulElement = this.querySelector('ul');
            ulElement.style.display = "block";
            toggleBackdrop(true);
            event.stopPropagation(); // Prevent the click event from propagating to the document
        });

        // Add a click event listener to the document
        document.addEventListener('click', function(event) {
            // Check if the clicked element is not within the ul element
            if (!event.target.closest('.item-order-wrapper')) {
                // Get all ul elements
                var ulElements = document.querySelectorAll('.item-order-wrapper ul');
                // Hide all ul elements
                ulElements.forEach(function(ulElement) {
                    ulElement.style.display = 'none';
                });
                // Toggle backdrop
                toggleBackdrop(false);
            }
        });
    });
});


function showNotification(message) {
    const noti = document.querySelector(".noti");

    noti.style.display = "block";
    let msg = noti.querySelector(".msg");
    msg.innerText = message;
    toggleBackdrop(true);
    document.addEventListener('click', function(event) {
        const isClickedInsideNotification = noti.contains(event.target);
        if (!isClickedInsideNotification) {
            noti.style.display = "none";
            toggleBackdrop(false);
        }
    });
}
