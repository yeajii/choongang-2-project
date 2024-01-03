export function initializeAutocomplete() {
    const homeworkTitles = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: '/homework/getHomeworkTitleListByKeyword?userId=' + $('#userId').val() + '&keyword=%QUERY',
            wildcard: '%QUERY'
        }
    });

    $('#title').typeahead({
        hint: true,
        highlight: true,
        minLength: 0
    }, {
        name: 'homework-titles',
        source: homeworkTitles
    });

    $('#title').keydown(function(e){
        if(e.which === 13){
            e.preventDefault();
        }
    });
}