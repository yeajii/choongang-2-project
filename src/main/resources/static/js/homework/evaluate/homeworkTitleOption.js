export default function homeworkTitleOption(title)  {
    const homeworkTitles = $('#homeworkTitles');

    function populateOptions(titles) {
        homeworkTitles.empty();
        homeworkTitles.append('<option value="">전체</option>');
        $.each(titles, function(index, title) {
            homeworkTitles.append('<option value="' + title + '">' + title + '</option>');
        });
    }

    function setSelectedOption(title) {
        const optionSelector = title ? 'option[value="' + title + '"]' : 'option[value=""]';
        $(optionSelector).attr('selected', 'selected');
    }

    function handleTitleChange() {
        homeworkTitles.on('change', function() {
            console.log("Selected title changed");
            const selectedTitle = $(this).val();
            $('form').append('<input type="hidden" name="title" value="' + selectedTitle + '" />').submit();
        });
    }

    $.ajax({
        url: '/homework/getHomeworkTitleList',
        type: 'GET',
        contentType: 'application/json',
        success: function(data) {
            // populateOptions(data);
            setSelectedOption(title);
            handleTitleChange();
        },
        error: function(error) {
            console.log("Error: ", error);
        }
    });
}