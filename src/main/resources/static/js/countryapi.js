document.addEventListener("DOMContentLoaded", () =>  {

    const selectDropdown = document.getElementById("countries");

    fetch("https://restcountries.com/v3.1/all").then(response => {
        return response.json();
        }).then(countries => {

            countries.sort((a, b) => {
                return a.name.common.localeCompare(b.name.common);
                    });

            let output = "";
            countries.forEach(country => {
                output += `<option value="${country.name.common}">${country.name.common + " " + country.flag}</option>`
            })

            selectDropdown.innerHTML = output;
        }).catch(error => {
            console.log(error);
        })

});
