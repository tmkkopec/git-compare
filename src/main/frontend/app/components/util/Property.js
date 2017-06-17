'use strict';

class Property {
    constructor(key, name, isShown = true) {
        this.key = key;
        this.name = name;
        this.isShown = isShown;
    }
}

export default Property;