function formInput(props, index) {

    if (props.type === 'select') {
        return formSelect(props, index);
    }
    if (props.type === 'file') {
        return `
        <div class="${props.classDiv}">
            <label>${props.label}</label>
            <input
                class="input-custom form-control"
                type="file"
                name="${props.name}"
                // id="${props.id ? props.id : ''}"
                onchange="onFileChange(event, ${index})"
                ${props.require ? 'required' : ''}
            />
            <span class="error">${props.message}</span>
            <img id="previewImage${index}" src="${props.value}" style="max-width: 150px; max-height: 150px; margin-top: 10px;">
        </div>
    `;
    }
    let pattern = '';
    if (props.pattern) {
        pattern = 'pattern="' + props.pattern + '"';
    }
    let disable = '';
    if (props.disable) {
        disable = 'disabled';
    }
    let min = '';
    if (props.min) {
        min = 'min="' + props.min + '"';
    }
    let max = '';
    if (props.max) {
        max = 'max="' + props.max + '"';
    }
    if (props.type === 'radio-group') {
        let radioButtons = '';
        props.options.forEach(option => {
            const checked = props.value === option.value ? 'checked' : '';
            radioButtons += `
                <input type="radio" class="btn-check input-custom" name="${props.name}" id="${props.name}${option.value}" value="${option.value}" autocomplete="off" ${checked}>
                <label class="btn btn-outline-primary" style="margin: 0 15px;padding:5px 35px; border-radius: 5px" for="${props.name}${option.value}">${option.name}</label>
            `;
        });
        return `
            <div class="${props.classDiv}">
                <label class="d-block">${props.label}</label>
                <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                    ${radioButtons}
                </div>
                <span class="error">${props.message}</span>
            </div>
        `;
    }

    return `
        <div class="${props.classDiv}">
            <label>${props.label}</label>
            <input class="input-custom form-control"
            type="${props.type || 'text'}"
            name="${props.name}"
            onblur="onFocus(${index})"
            ${min}
            ${max}
            ${pattern}
            ${disable}
            value="${props.value}"
            ${props.require ? 'required' : ''} /></br>
            <span class="error">${props.message}</span>
        </div>
    `;
}

function formSelect(props, index) {
    let optionsStr = "";
    props.options.forEach(e => {
        if(props.value === e.value){
            optionsStr += `<option value="${e.value}" selected>${e.name}</option>`;
        }else {
            optionsStr += `<option value="${e.value}" >${e.name}</option>`;
        }
    })
    const optionSelected = props.options.find(e => e.value === props.value);
    if(props.disable){
        optionsStr = `<option selected>${optionSelected.name}</option>`;
    }
    let disable = '';
    if(props.disable){
        disable = 'disabled';
    }
    return `<div class="${props.classDiv}">
                <label>${props.label}</label>
                    <select class="input-custom form-control"
                    type="${props.type || 'text'}" name="${props.name}"
                    onblur="onFocus(${index})"
                    ${disable}
                    value="${props.value}"
                    ${props.require ? 'required' : ''}>
                        <option value>---Choose---</option>
                        ${optionsStr}
                    </select>
                    </br>
                    <span class="error">${props.message}</span>
            </div>`
}
const onFocus = (index) => {
    const inputsForm = document.querySelectorAll('#formBody .input-custom')
    inputsForm[index].setAttribute("focused", "true");
}

document.addEventListener('invalid', (function () {
    return function (e) {
        e.preventDefault();
        e.target.onblur();
    };
})(), true);