
# Warehouse Service

This repository is one of the three repositories ([Water](https://github.com/KDG-Muhammet/Water), [Land](https://github.com/KDG-Muhammet/Land) and Warehouse) that together form the MineralFlow logistics platform.  
Each repository runs as a separate Spring Boot application with its own database schema and communicates with the others through REST APIs.

## Overview
The **Warehouse Service** is the central inventory system.  
It manages stock, calculates storage costs, tracks commissions, and generates invoices.

## Features
- Maintain inventory with overflow capacity up to **110 %** of nominal limits.
- Calculate daily storage costs and **1 % commission** on fulfilled POs.
- Generate daily invoices and provide financial reports.

## API Highlights
- `GET /inventory` – Retrieve current stock levels.
- `POST /calculateStorage` – Compute storage costs.
- `POST /calculateCommission` – Compute commission for fulfilled POs.
- `GET /invoices` – Retrieve generated invoices.

## Tech Stack
- **Spring Boot** (Java)
- **PostgreSQL** – Schema: `warehouse`
- REST endpoints consumed by Water and Land services
- **OAuth2 / Keycloak** security
