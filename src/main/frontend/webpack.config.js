"use strict";

const HTMLWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');

const HTMLWebpackPluginConfig = new HTMLWebpackPlugin({
    template: __dirname + '/../resources/static/index.html',
    filename: 'index.html',
    inject: 'body'
});

const UglifyJsPlugin = new webpack.optimize.UglifyJsPlugin({
    minimize: true
});

module.exports = {
    entry: __dirname + '/app/index.js',
    module: {
        loaders: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'babel-loader'
            }
        ]
    },
    output: {
        filename: 'bundle.js',
        path: __dirname + '/../resources/static/js/generated'
    },
    plugins: [
        UglifyJsPlugin
    ]
};
