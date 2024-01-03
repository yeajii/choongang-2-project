export default function gameSelection() {
    $('#contentId').change(function() {
        const selectedContentId = $(this).val();
        $('form').append('<input type="hidden" name="contentId" value="' + selectedContentId + '" />').submit();
    });
}